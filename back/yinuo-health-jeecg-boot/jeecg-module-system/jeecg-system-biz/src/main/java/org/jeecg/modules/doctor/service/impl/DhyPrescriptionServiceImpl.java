package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.DhyPrescriptionRequest;
import org.jeecg.modules.doctor.entity.DhyPrescriptionResponse;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import org.jeecg.modules.doctor.service.IDhyPrescriptionService;
import org.jeecg.modules.doctor.util.InterfaceInfo;
import org.jeecg.modules.doctor.util.LogUtilNew;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 东华原煎药系统服务实现类
 * @Author: lihaoran
 * @Date: 2026-01-10
 * @Version: V1.0
 */
@Slf4j
@Service
public class DhyPrescriptionServiceImpl implements IDhyPrescriptionService {

    /**
     * 东华原接口地址
     * 测试地址: http://111.204.86.202:7012/api/PreScription/dhyPrescriptionService
     * 可以在配置文件中配置 dhy.prescription.url
     */
    @Value("${dhy.prescription.url:http://111.204.86.202:7012/api/PreScription/dhyPrescriptionService}")
    private String dhyPrescriptionUrl;

    /**
     * 请求超时时间（毫秒）
     */
    private static final int TIMEOUT = 30000;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<DhyPrescriptionResponse> dhyPrescriptionService(DhyPrescriptionRequest request, PeRegisterList peRegister) {
        // 将请求对象转换为JSON字符串
        String jsonStr = JSONUtil.toJsonStr(request);
        return dhyPrescriptionService(jsonStr, peRegister);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<DhyPrescriptionResponse> dhyPrescriptionService(String jsondhy, PeRegisterList peRegister) {
        // 记录接口请求日志，按患者维度记录
        LogUtilNew logUtil = LogUtilNew.getInstance(InterfaceInfo.DHY_PRESCRIPTION_SERVICE, peRegister);

        try {
            // 参数校验
            if (StrUtil.isBlank(jsondhy)) {
                log.error("东华原处方传入接口：请求参数为空");
                return Result.error("请求参数不能为空");
            }

            // 验证JSON格式
            if (!JSONUtil.isJson(jsondhy)) {
                log.error("东华原处方传入接口：请求参数不是有效的JSON格式");
                return Result.error("请求参数必须是有效的JSON格式");
            }

            // 记录发送消息
            logUtil.setSendMessage(jsondhy);
            log.info("东华原处方传入接口：开始发送请求，URL: {}", dhyPrescriptionUrl);
            log.info("东华原处方传入接口：请求参数: {}", jsondhy);

            // 构建HTTP POST请求
            HttpRequest httpRequest = HttpRequest.post(dhyPrescriptionUrl)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(jsondhy)
                    .timeout(TIMEOUT);

            // 发送请求并获取响应
            HttpResponse response = httpRequest.execute();
            String responseBody = response.body();

            // 记录接收消息
            logUtil.setReceiveMessage(responseBody);
            log.info("东华原处方传入接口：收到响应，HTTP状态码: {}", response.getStatus());
            log.info("东华原处方传入接口：响应内容: {}", responseBody);

            // 检查HTTP状态码
            if (!response.isOk()) {
                log.error("东华原处方传入接口：HTTP请求失败，状态码: {}", response.getStatus());
                logUtil.success(false);
                logUtil.log("HTTP请求失败，状态码: " + response.getStatus());
                return Result.error("HTTP请求失败，状态码: " + response.getStatus());
            }

            // 验证响应内容是否为JSON
            if (!JSONUtil.isJson(responseBody)) {
                log.error("东华原处方传入接口：响应内容不是有效的JSON格式");
                logUtil.success(false);
                logUtil.log("响应内容不是有效的JSON格式: " + responseBody);
                return Result.error("响应内容不是有效的JSON格式");
            }

            // 解析响应
            DhyPrescriptionResponse dhyResponse = JSONUtil.toBean(responseBody, DhyPrescriptionResponse.class);

            // 判断业务是否成功
            if (dhyResponse.isSuccess()) {
                log.info("东华原处方传入接口：处方推送成功");
                logUtil.success(true);
                logUtil.log("处方推送成功");
                return Result.OK("处方推送成功", dhyResponse);
            } else {
                log.error("东华原处方传入接口：处方推送失败，错误码: {}, 错误信息: {}",
                        dhyResponse.getDhyErrorCode(), dhyResponse.getDhyErrorMsg());
                logUtil.success(false);
                logUtil.log(String.format("处方推送失败，错误码: %s, 错误信息: %s",
                        dhyResponse.getDhyErrorCode(), dhyResponse.getDhyErrorMsg()));
                return Result.error(dhyResponse.getDhyErrorMsg(), dhyResponse);
            }

        } catch (Exception e) {
            log.error("东华原处方传入接口：发生异常", e);
            logUtil.success(false);
            logUtil.log("发生异常: " + e.getMessage());
            return Result.error("处方推送异常: " + e.getMessage());
        } finally {
            // 保存日志
            logUtil.saveLog();
        }
    }
}