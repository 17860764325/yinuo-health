package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.InterfaceRequestLog;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import org.jeecg.modules.doctor.mapper.PeRegisterListMapper;
import org.jeecg.modules.doctor.service.IPeRegisterListService;
import org.jeecg.modules.doctor.util.*;
import org.jeecg.modules.doctor.vo.PersonCreateResponse;
import org.jeecg.modules.doctor.vo.PersonSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 人员信息查询
 * @Author: jeecg-boot
 * @Date: 2023-10-09
 * @Version: V1.0
 */
@Service
public class PeRegisterListServiceImpl extends ServiceImpl<PeRegisterListMapper, PeRegisterList> implements IPeRegisterListService {

    private static final String TYPE = "type";
    private static final String PAT_NAME = "patName";
    private static final String ID_CARD_TYPE = "idCardType";
    private static final String ID_CARD_NO = "idCardNo";

    private static final InterfaceInfo INTERFACE_INFO = InterfaceInfo.PERSON_SEARCH;
    private static final InterfaceInfo INTERFACE_INFO_PERSON_CREATE = InterfaceInfo.PERSON_CREATE;
    private static final InterfaceInfo LOG_TEST = InterfaceInfo.LOG_TEST;

    private static final String SEX = "sex";
    private static final String BIRTH_DAY = "birthday";
    private static final String PHONE = "phone";
    private static final String CARD_TYPE = "cardType";
    private static final String CARD_NO = "cardNo";
    private static final String HOSPITAL_ID = "hospitalId";
    private static final String ORG_ID = "orgId";

    @Autowired
    private LogUtil logUtil;


    /**
     * @description: 人员档案查询
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:24 AM
     */
    @Override
    @Transactional
    public Result personSearch(List<String> ids) {
        StringBuffer resultMessage = new StringBuffer();
        // 根据 id 查询出对应的 数据
        List<PeRegisterList> peRegisterLists = this.listByIds(ids);
        // 循环数据组装参数，并且发送请求，将请求返回的信息做存储
        for (PeRegisterList peRegister : peRegisterLists) {
            // 记录接口请求日志
            InterfaceRequestLog log = new InterfaceRequestLog();
            // log是否成功标志
            boolean flag = true;
            // log-remoark日志信息
            StringBuffer logMessage = new StringBuffer();
            try {
                // 日志基础信息封装
                logUtil.logBasicInfo(peRegister, INTERFACE_INFO, log);
                // 参数封装
                Map<String, Object> paramsMap = new HashMap<>();
                // type是字符串传1时1根据patId查询，patId字段必填；传2时1根据姓名和身份证号查询，patName、idCardType、idCardNo字段必填；传3时1根据卡号或者电子健康码查询，cardNo字段必填；
                paramsMap.put(TYPE, StaticValue.TWO.getCode());
                // 患者姓名
                paramsMap.put(PAT_NAME, peRegister.getPatientName());
                // 证件类型编号，身份证传0，其他从2.1.8.3中取值；填写证件号时证件类型必填
                paramsMap.put(ID_CARD_TYPE, StaticValue.ZERO.getCode());
                // 证件号
                paramsMap.put(ID_CARD_NO, peRegister.getPersonNo());
                // 定义返回的请求数据
                PersonSearchResponse personSearchResponse = null;
                // 请求参数记录日志
                log.setSendMessage(JSONUtil.parse(paramsMap).toString());
                // 发送请求
//                String res = RequestUtil.go(INTERFACE_INFO.getUrl(), INTERFACE_INFO.getRequestType(), paramsMap);
                String res = "{\n" +
                        "  \"success\" : true,\n" +
                        "  \"decorate\" : true,\n" +
                        "  \"code\" : \"0000\",\n" +
                        "  \"message\" : \"成功\",\n" +
                        "  \"data\" : {\n" +
                        "    \"patName\" : \"郑华珍\",\n" +
                        "    \"patId\" : \"2156980\",\n" +
                        "    \"sex\" : \"女\",\n" +
                        "    \"idCardType\" : \"0\",\n" +
                        "    \"idCardNo\" : \"370702194709182247\",\n" +
                        "    \"birthday\" : \"1947-09-17 23:00:00\",\n" +
                        "    \"phone\" : \"13780817680\",\n" +
                        "    \"cardType\" : \"3\",\n" +
                        "    \"cardNo\" : \"370702194709182247\",\n" +
                        "    \"nation\" : \"1\",\n" +
                        "    \"nationName\" : \"汉族\",\n" +
                        "    \"country\" : \"1\",\n" +
                        "    \"countryName\" : null,\n" +
                        "    \"province\" : null,\n" +
                        "    \"provinceName\" : null,\n" +
                        "    \"city\" : null,\n" +
                        "    \"cityName\" : null,\n" +
                        "    \"town\" : null,\n" +
                        "    \"townName\" : null,\n" +
                        "    \"addr\" : \"山东省潍坊市奎文区大虞街道东风东街239号六号楼三单元四楼东\",\n" +
                        "    \"chargeClassId\" : \"103\",\n" +
                        "    \"chargeClassName\" : \"自费\",\n" +
                        "    \"sexId\" : \"2\",\n" +
                        "    \"sexName\" : \"女\",\n" +
                        "    \"postCode\" : \"\",\n" +
                        "    \"maritalStatusId\" : null,\n" +
                        "    \"professionId\" : null,\n" +
                        "    \"companyName\" : null,\n" +
                        "    \"contactTelephone\" : \"\",\n" +
                        "    \"contactName\" : \"\",\n" +
                        "    \"healthCardNumber\" : \"\",\n" +
                        "    \"hisCreaterId\" : \"-1\",\n" +
                        "    \"hisCreaterName\" : \"导入老数据\",\n" +
                        "    \"hisCreateTime\" : \"2022-12-08 14:11:27\",\n" +
                        "    \"hospitalId\" : \"10353001\",\n" +
                        "    \"maritalStatusName\" : null,\n" +
                        "    \"orgId\" : \"10353\"\n" +
                        "  },\n" +
                        "  \"traceId\" : \"8fa3066b-e8d8-43c2-9ec0-c37372ee563a\",\n" +
                        "  \"exceptionName\" : null,\n" +
                        "  \"qualityMonitor\" : null,\n" +
                        "  \"ignoreQualityMonitor\" : false,\n" +
                        "  \"level\" : \"info\",\n" +
                        "  \"service\" : \"msun-middle-aggregate-patient\",\n" +
                        "  \"businessException\" : false\n" +
                        "}";
                // 记录返回数据
                log.setReceiveMessage(res);
                // json 转换
                if (JSONUtil.isJson(res)) {
                    // 将字符串转换为实体类
                    personSearchResponse = JSONUtil.toBean(res, PersonSearchResponse.class);
                    // 判断请求是否成功
                    if (!personSearchResponse.getSuccess()) {
                        flag = false;
                        logUtil.logMessage(resultMessage, peRegister, logMessage, "请求失败：" + personSearchResponse.getMessage());
                    } else {
                        if (BeanUtil.isNotEmpty(personSearchResponse.getData()) && StrUtil.isNotEmpty(personSearchResponse.getData().getPatId())) {
                            // 获取请求数据的 患者 id 字段，维护到 pe_regiter_list 表格中
                            peRegister.setPatId(personSearchResponse.getData().getPatId());
                            // 书写返回信息
                            logUtil.logMessage(resultMessage, peRegister, logMessage, "获取 患者id(" + personSearchResponse.getData().getPatId() + ") 成功！");
                            // 将更新的数据进行持久化
                            boolean b = this.saveOrUpdate(peRegister);
                            flag = b;
                            logMessage.append("数据维护：" + b);
                        } else {
                            flag = false;
                            // 书写失败返回信息 和 log
                            logUtil.logMessage(resultMessage, peRegister, logMessage, "获取 患者id 失败！该人未创建档案，请创建档案后，在维护患者id");
                        }
                    }
                } else {
                    // 如果返回的数据不是json数据的话
                    flag = false;
                    logUtil.logMessage(resultMessage, peRegister, logMessage, "接口返回信息不是json数据！");
                }
            } catch (Exception e) {
                flag = false;
                logUtil.logMessage(resultMessage, peRegister, logMessage, "体检号：" + peRegister.getPatientNo() + ",报错：" + e.getMessage());
            } finally {
                // 记录日志 flag
                log.setSuccess(flag ? StaticValue.SUCCESS.getCode() : StaticValue.FAIL.getCode());
                // 记录失败原因
                log.setRemark(logMessage.toString());
                // 将日志保存
                logUtil.saveLog(log);
            }
        }
        // 数据持久化
        return Result.OK(resultMessage.toString());

    }

    /**
     * @description: 档案创建接口
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/12/23 10:38 PM
     */
    @Override
    public Result personCreatete(List<String> ids) {
        // 返回的信息
        StringBuffer resultMessage = new StringBuffer();
        // 根据 id 查询出对应的 数据
        List<PeRegisterList> peRegisterLists = this.listByIds(ids);
        // 循环人员数据，进行档案创建
        for (PeRegisterList peRegister : peRegisterLists) {
            // 记录接口请求日志
            InterfaceRequestLog log = new InterfaceRequestLog();
            // log是否成功标志
            boolean flag = true;
            // log-remoark日志信息
            StringBuffer logMessage = new StringBuffer();
            try {
                // 日志基础信息封装
                logUtil.logBasicInfo(peRegister, INTERFACE_INFO_PERSON_CREATE, log);

                // 组装请求参数
                Map<String, Object> paramMap = new HashMap<>();
                personCreateParam(peRegister, paramMap);
                // 将组装的参数，存到日志
                log.setSendMessage(JSONUtil.parse(paramMap).toString());

                // 发送请求
//                String res = RequestUtil.go(INTERFACE_INFO_PERSON_CREATE.getUrl(), INTERFACE_INFO_PERSON_CREATE.getRequestType(), paramMap);
                // 假数据
                String res = "{\n" +
                        "    \"businessException\": true,\n" +
                        "    \"code\": \"\",\n" +
                        "    \"data\": {\n" +
                        "        \"cardNo\": \"\",\n" +
                        "        \"cardType\": 0,\n" +
                        "        \"patId\": \"test132\"\n" +
                        "    },\n" +
                        "    \"decorate\": true,\n" +
                        "    \"exceptionName\": \"\",\n" +
                        "    \"ignoreQualityMonitor\": true,\n" +
                        "    \"message\": \"\",\n" +
                        "    \"success\": true,\n" +
                        "    \"traceId\": \"\"\n" +
                        "}";
                // 日志记录接受信息
                log.setReceiveMessage(res);

                // 处理接受信息
                if (!JSONUtil.isJson(res)) {
                    // 如果返回的数据不是json数据
                    flag = false;
                    logUtil.logMessage(resultMessage, peRegister, logMessage, "接口返回信息不是json数据！");
                    continue;
                }
                // 接受信息
                PersonCreateResponse response = JSONUtil.toBean(res, PersonCreateResponse.class);

                // 判空
                if (BeanUtil.isEmpty(response) || BeanUtil.isEmpty(response.getData()) || StrUtil.isEmpty(response.getData().getPatId())) {
                    flag = false;
                    logUtil.logMessage(resultMessage, peRegister, logMessage, "接口返回信息为空！");
                    continue;
                }

                // 判断请求是否成功
                if (!response.getSuccess()) {
                    flag = false;
                    logUtil.logMessage(resultMessage, peRegister, logMessage, "接口请求失败！");
                    continue;
                }

                // 维护数据，将请求到的患者ID 维护到 本地数据库上
                // TODO 还有一个卡号，和卡类型编号需要确认是否有用到的地方，需要不需要存储，这里并没有存储处理
                peRegister.setPatId(response.getData().getPatId());
                // 日志和返回消息处理
                logUtil.logMessage(resultMessage, peRegister, logMessage, "创建档案成功，并接收到患者ID(" + response.getData().getPatId() + ")");
                // 数据持久化
                boolean b = this.saveOrUpdate(peRegister);
                flag = b;
                logMessage.append("数据持久化是否成功：" + b);

            } catch (Exception e) {
                flag = false;
                // 日志log 返回的数据
                logUtil.logMessage(resultMessage, peRegister, logMessage, "报错：" + e.getMessage());
            } finally {
                // 记录日志 flag
                log.setSuccess(flag ? StaticValue.SUCCESS.getCode() : StaticValue.FAIL.getCode());
                // 记录失败原因
                log.setRemark(logMessage.toString());
                // 将日志保存
                logUtil.saveLog(log);
            }
        }
        // 数据持久化
        return Result.OK(resultMessage.toString());
    }

    /**
     * @description: 日志测试
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/13/23 2:42 AM
     */
    @Override
    public Result newLogTest(List<String> ids) {
        List<PeRegisterList> peRegisterLists = this.listByIds(ids);
        // 循环人员数据，进行档案创建
        for (PeRegisterList peRegister : peRegisterLists) {
            LogUtilNew log = LogUtilNew.getInstance(LOG_TEST, peRegister);
            log.setSendMessage("sendMessage");
            log.setReceiveMessage("receiveMessage");
            log.log("log 方法记录");
            log.log("赋值请求失败");
            log.errorLog("errorLog 记录");
            log.log("赋值请求成功");
            log.success(true);
            StringBuffer resultMessage = log.log("保存日志");
            log.log("这是最终返回的日志"+resultMessage);
            log.saveLog();
        }
        return Result.ok("测试结束");
    }


    private void personCreateParam(PeRegisterList peRegister, Map<String, Object> paramMap) {
        // 患者姓名
        paramMap.put(PAT_NAME, peRegister.getPatientName());
        // 患者性别编号 1:男，2:女 0:未知性别，9:未说明性别;(因为自己数据库中的性别是按照：男，女拉村的，所以需要对比，然后转换为编码在作为参数)
        paramMap.put(SEX, StaticValue.MAN.getText().equals(peRegister.getSex()) ? StaticValue.MAN.getCode() : StaticValue.WOMAN.getCode());
        // 证件类型编号: 身份证号： 0
        paramMap.put(ID_CARD_TYPE, StaticValue.ZERO.getCode());
        // 证件号
        paramMap.put(ID_CARD_NO, peRegister.getPersonNo());
        // 出生日期
        paramMap.put(BIRTH_DAY, peRegister.getBirthday());
        // 手机号
        paramMap.put(PHONE, peRegister.getTelphone());
        // 卡类型编号，需要对接2.1.8接口后，商议确认
        paramMap.put(CARD_TYPE, "");
        // 卡号，需要对接2.1.8接口后，商议确认
        paramMap.put(CARD_NO, "");
        // 医院ID
        paramMap.put(HOSPITAL_ID, StaticValue.HOSPITAL_ID.getCode());
        // 机构ID
        paramMap.put(ORG_ID, StaticValue.ORG_ID.getCode());
    }
}
