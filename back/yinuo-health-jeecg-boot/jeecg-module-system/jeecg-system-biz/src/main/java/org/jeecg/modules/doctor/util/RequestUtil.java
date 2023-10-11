package org.jeecg.modules.doctor.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import cn.hutool.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

/**
 * @author lihaoran
 * @date 10/10/23 6:28 PM
 * 众阳接口，请求访问的工具类
 */
public class RequestUtil {

    private static final String APP_ID = "app6096163592757055371";
    private static final String APP_SECRET = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALR+DjmEgIGx0ZMgX+7iCTlF5/4dOzKwJBZf+DHNiq6Z/OeDIlMOL/D3vgWv76Fg4AWiCBG4ucqU5G1JUu2IS4wUOAFMsHVGbSWORl587vtXf4z9JxiB1epMInDKpBYiGXecC1NS1feyRBPMKZY9Wcr1GWi/ljxMtzARad9tEe1nAgMBAAECgYEApAGzLUKgfrk0pUsSwP91wCwsfTTUmN+DOy2jWqXZsYRNUZVP+EZ4+64yZfqTdYGQrq2oRWoksNcQpdmt2Bc0BSJsX7DbBz9Cd/rwDP/m/d96MB68RK64Y1/bXNsAevL5GJ8Y/JGn3/V08w5Qly9Ot495fxW0Wd0QqEdqaomo6QECQQDottwk3jpi2TcrB6HpvPJ0vz65QQLMh2LtMLO9+wh6OPdFp9qW8CAisPlO8D2P+y00/p3uAELv6gHo6OLylTaBAkEAxo1+xbxiBjtkLsGSbNL2bceTqEmux/9vIPF8b7Zz+YdjYBe8dIA3A9vJNtc291gqCseNLl2hjpafTr0v9gQ/5wJACqKIrvqk6m2I0uOXZ6ol3mX7BNZOvXAWekZ2gGEfgw5lZn6EQ8+XeF5kFlJuTc0pxLk6GG1Uocgs7CrwbCubgQJBAI//ipOJ3mW6qRun07QNBB/2AEaquZHe1Q/FU4QJhO7Rm+bTECBF5KBtw+58Ayc7z7Hlq6SWz1aEMqF+X8xCpL0CQCdP1JoaaGkYrRTVYXQi0G1ttK5Sqchh2FssyOWRHq5S9KH/dc9G2hWrn2Ci9WxvKxuI607MHDGVPSMkpRlJ9qo=";

    @Value("thirdInterface.ip")
    private static  String IP;

    private static final String GET = "get";

    private static final String POST = "post";


    public static String go(String url,String requestType,Map<String, Object> paramsMap) throws Exception{
        String message = "";
        if (requestType.equals(GET)){
            message = get(url,paramsMap);
        }else {
            message = post(url,paramsMap);
        }
        return message;
    }

    /**
     * POST请求
     *
     * @param url       请求接口地址
     * @param paramsMap 请求参数，可以为空
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, Object> paramsMap) throws Exception {
        // 参数不为空，转换成标准JSON字符串
        String paramsJsonStr = "";
        if (CollectionUtil.isNotEmpty(paramsMap)) {
            paramsJsonStr = JSON.toJSONString(paramsMap);
        }

        System.out.println(paramsJsonStr);
        // 当前时间戳，与header头中的timestamp一致
        long timestamp = System.currentTimeMillis();

        // 待MD5加密字符串 = 请求字符串 + 当前时间戳
        String signatureStr = paramsJsonStr + timestamp;

        // MD5进行摘要加密
        MessageDigest md = MessageDigest.getInstance("MD5");
        String md5Str = Hex.encodeHexString(md.digest(signatureStr.getBytes(StandardCharsets.UTF_8)));

        // 根据MD5摘要进行签名
        String sign = getSign(md5Str, APP_SECRET);

        // 构建请求头
        Map<String, String> headers = builderHeaders(sign, timestamp);

        // 构建请求，可以用RestTemplate、HttpClient、  WebFlux等工具类，具体看自身项目工具类使用，不做限制
        HttpRequest postHttpReq = HttpUtil.createPost(url);
        // 加入定义的请求头
        postHttpReq.addHeaders(headers);
        // 加入请求参数，转换成JSON后的
        postHttpReq.body(paramsJsonStr);
        // 发起请求
        HttpResponse response = postHttpReq.execute();
        // 返回结果body
        String body = response.body();
        return body;
    }

    /**
     * GET请求
     *
     * @param url       请求接口地址
     * @param paramsMap 请求参数，可以为空
     * @return
     */
    public static String get(String url, Map<String, Object> paramsMap) throws Exception {
        // 请求参数不为空，按照ASCII码自然排序
        Map<String, Object> getParamsMap = new TreeMap<>(Comparator.naturalOrder());
        if (CollectionUtil.isNotEmpty(paramsMap)) {
            paramsMap.entrySet().forEach(n -> getParamsMap.put(n.getKey(), n.getValue()));
        }

        // 排序后的请求参数字符串
        String sortedParamsStr = "";
        if (CollectionUtil.isNotEmpty(getParamsMap)) {
            Set<String> keySet = getParamsMap.keySet();
            Iterator<String> iter = keySet.iterator();
            StringBuilder sb = new StringBuilder();
            while (iter.hasNext()) {
                String key = iter.next();
                sb.append(key).append("=").append(getParamsMap.get(key)).append("&");
            }
            sortedParamsStr = sb.deleteCharAt(sb.length() - 1).toString();
        }

        // 当前时间戳，与header头中的timestamp一致
        long timestamp = System.currentTimeMillis();

        // 待签名字符串 = 排序后请求字符串 + 当前时间戳
        String signatureStr = sortedParamsStr + timestamp;

        // 执行签名
        String sign = getSign(signatureStr, APP_SECRET);

        // 构建请求头
        Map<String, String> headers = builderHeaders(sign, timestamp);

        // 有请求参数，拼接到地址栏，比  如 https://openapi.msunhis.com/msun-his-app-emr-openapi/v1/list?a=1&c=3&b=2
        if (StrUtil.isNotBlank(sortedParamsStr)) {
            url = url + "?" + sortedParamsStr;
        }

        // 构建请求，可以用RestTemplate、HttpClient、 WebFlux等工具类，具体看自身项目工具类使用，不做限制
        HttpRequest getHttpReq = HttpUtil.createGet(url);
        // 加入定义的请求头
        getHttpReq.addHeaders(headers);
        // 发起请求
        HttpResponse response = getHttpReq.execute();
        // 返回结果body
        String body = response.body();
        return body;
    }

    /**
     * 生成签名
     *
     * @param preStr    待签名字符串
     * @param appSecret 私钥字符串
     * @return base64字符串
     * @throws Exception
     */
    public static String getSign(String preStr, String appSecret) throws Exception {
        appSecret = appSecret.replace("-----    BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replace("\r", "").replace("\n", "").trim();
        String suite = "SHA256WithRSA";

        //初始化算法SHA256
        Signature signature = Signature.getInstance(suite);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(appSecret));
        //初始化私钥+RSA
        KeyFactory keyFac = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFac.generatePrivate(keySpec);
        signature.initSign(privateKey);

        //待签名字符串转byte数组使用UTF8
        byte[] msgBuf = preStr.getBytes("UTF8");
        signature.update(msgBuf);
        byte[] byteSign = signature.sign();
        //签名值byte数组转字符串用BASE64
        String strSign = Base64.encodeBase64String(byteSign);
        return strSign;
    }

    /**
     * 构建http请求头
     *
     * @param sign
     * @param timestamp
     * @return
     */
    public static Map<String, String> builderHeaders(String sign, long timestamp) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("appId", APP_ID);
        headersMap.put("signType", "RSA2");
        headersMap.put("orgId", "10353");
        headersMap.put("hospitalId", "10353001");
        headersMap.put("sign", sign);
        headersMap.put("timestamp", timestamp + "");
        return headersMap;
    }
}
