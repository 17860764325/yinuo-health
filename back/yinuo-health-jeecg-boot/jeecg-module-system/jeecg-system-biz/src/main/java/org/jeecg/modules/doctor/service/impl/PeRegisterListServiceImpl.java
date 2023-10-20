package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import net.sf.saxon.om.Item;
import org.apache.poi.ss.formula.functions.Offset;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.*;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;
import org.jeecg.modules.doctor.mapper.PeRegisterListMapper;
import org.jeecg.modules.doctor.mapper.PeReportDepartmentDetailMapper;
import org.jeecg.modules.doctor.service.*;
import org.jeecg.modules.doctor.util.*;
import org.jeecg.modules.doctor.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMAT;
import static java.lang.Double.parseDouble;

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

    // 人员查询
    private static final InterfaceInfo INTERFACE_INFO = InterfaceInfo.PERSON_SEARCH;
    // 档案创建
    private static final InterfaceInfo INTERFACE_INFO_PERSON_CREATE = InterfaceInfo.PERSON_CREATE;
    // 日志测试
    private static final InterfaceInfo LOG_TEST = InterfaceInfo.LOG_TEST;
    // LIS检验申请
    private static final InterfaceInfo LIS_APPLY = InterfaceInfo.LIS_APPLY;
    // 条码生成
    private static final InterfaceInfo BAR_CODE_BUILD = InterfaceInfo.BAR_CODE_BUILD;
    // 报告ID查询
    private static final InterfaceInfo REPORT_ID_SEARCH = InterfaceInfo.REPORT_ID_SEARCH;
    // 报告查询
    private static final InterfaceInfo REPORT_DETAIL_SEARCH = InterfaceInfo.REPORT_DETAIL_SEARCH;

    private static final String SEX = "sex";
    private static final String BIRTH_DAY = "birthday";
    private static final String PHONE = "phone";
    private static final String CARD_TYPE = "cardType";
    private static final String CARD_NO = "cardNo";
    private static final String HOSPITAL_ID = "hospitalId";
    private static final String ORG_ID = "orgId";
    private static final String PAT_ID = "patId";
    private static final String PAT_NO = "patNo";
    private static final String ID_CARD = "idCard";
    private static final String PAT_AGE = "patAge";
    private static final String PAT_TYPE = "patType";
    private static final String PAT_PHONE = "patPhone";
    private static final String PAT_DEPT_ID = "patDeptId";
    private static final String PAT_DEPT_NAME = "patDeptName";
    private static final String REQ_DOC_ID = "reqDocId";
    private static final String REQ_DOC_NAME = "reqDocName";
    private static final String ORDER_ITEM_LIST = "orderItemList";
    private static final String LAB_ITEM_ID = "labItemId";
    private static final String MAIN_ORDER_ID = "mainOrderId";
    private static final String NOTE = "note";
    private static final String ORDER_ID = "orderId";
    private static final String ORDER_NAME = "orderName";
    private static final String PRICE = "price";
    private static final String SAMPLE_CLASS_ID = "sampleClassId";
    private static final String SAMPLE_CLASS_NAME = "sampleClassName";
    private static final String REPORT_ITEM_ID = "reportItemId";
    private static final String EXECUTE_STATE = "executeState";
    private static final String MAIN_ORDER_ID_LIST = "mainOrderIdList";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String BAR_CODE_LIST = "barcodeList";
    private static final String REPORT_ID = "reportId";

    @Autowired
    private LogUtil logUtil;

    @Autowired
    private PeRegisterListMapper peRegisterListMapper;

    @Autowired
    private ICheckProjectService checkProjectService;

    @Autowired
    private ICheckProjectDetailService checkProjectDetailService;

    @Autowired
    private ICurAssayMasterService curAssayMasterService;

    @Autowired
    private ILisApplyBarCodeReportIdService lisApplyBarCodeReportIdService;

    @Autowired
    private IReportDetailService reportDetailService;

    @Autowired
    private IPeReportDepartmentDetailService peReportDepartmentDetailService;

    @Autowired
    private PeReportDepartmentDetailMapper peReportDepartmentDetailMapper;


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
            personSearchOne(resultMessage, peRegister);
        }
        // 数据持久化
        return Result.OK(resultMessage.toString());

    }

    private void personSearchOne(StringBuffer resultMessage, PeRegisterList peRegister) {
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

    /**
     * @description: 档案创建接口
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/12/23 10:38 PM
     */
    @Override
    @Transactional
    public Result personCreatete(List<String> ids) {
        // 返回的信息
        StringBuffer resultMessage = new StringBuffer();
        // 根据 id 查询出对应的 数据
        List<PeRegisterList> peRegisterLists = this.listByIds(ids);
        // 循环人员数据，进行档案创建
        for (PeRegisterList peRegister : peRegisterLists) {
            personCreateOne(resultMessage, peRegister);
        }
        // 数据持久化
        return Result.OK(resultMessage.toString());
    }

    private void personCreateOne(StringBuffer resultMessage, PeRegisterList peRegister) {
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
                return;
            }
            // 接受信息
            PersonCreateResponse response = JSONUtil.toBean(res, PersonCreateResponse.class);

            // 判空
            if (BeanUtil.isEmpty(response) || BeanUtil.isEmpty(response.getData()) || StrUtil.isEmpty(response.getData().getPatId())) {
                flag = false;
                logUtil.logMessage(resultMessage, peRegister, logMessage, "接口返回信息为空！");
                return;
            }

            // 判断请求是否成功
            if (!response.getSuccess()) {
                flag = false;
                logUtil.logMessage(resultMessage, peRegister, logMessage, "接口请求失败！");
                return;
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

    /**
     * @description: LIS检验申请
     * @param: lisApplyInfo
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/16/23 9:50 PM
     */
    @Override
    @Transactional
    public Result LISApply(LISApplyInfo lisApplyInfo) {
        // 创建最终返回的信息
        StringBuffer resultAll = new StringBuffer();
        // 获取用户信息
        List<PeRegisterList> peRegisters = this.listByIds(lisApplyInfo.getPatIds());
        // 循环操作用户
        for (PeRegisterList peRegister : peRegisters) {
            LISApplyOne(lisApplyInfo, resultAll, peRegister);
        }
        // 返回信息
        return Result.ok(resultAll.toString());
    }

    private void LISApplyOne(LISApplyInfo lisApplyInfo, StringBuffer resultAll, PeRegisterList peRegister) {
        StringBuffer result = null;
        // 创建日志记录
        LogUtilNew log = LogUtilNew.getInstance(LIS_APPLY, peRegister);
        try {
            log.log("LIS检验申请方法调用！｜");
            // 获取用户检验项目
            List<PatItems> patItems = peRegisterListMapper.getPatItems(peRegister.getPatientNo());
            log.log("获取到的用户检验项目：" + JSONUtil.parse(patItems) + "｜");
            if (CollUtil.isEmpty(patItems)) {
                result = log.resultLog("查询到检查项目为空无法生成LIs检验申请！");
                log.log("查询到检查项目为空无法生成LIS检验申请！");
                log.success(false);
                return;
            }
            // 组装参数
            // 组装主参数；
            Map<String, Object> paramMap = new HashMap<>();
            // 患者id
            paramMap.put(PAT_ID, peRegister.getPatId());
            // 患者体检流水号
            paramMap.put(PAT_NO, peRegister.getPatientNo());
            // 患者姓名
            paramMap.put(PAT_NAME, peRegister.getPatientName());
            // 身份证号
            paramMap.put(ID_CARD, peRegister.getPersonNo());
            // 性别
            paramMap.put(SEX, peRegister.getSex());
            // 年龄
            paramMap.put(PAT_AGE, peRegister.getAge());
            // 出生日期
            paramMap.put(BIRTH_DAY, peRegister.getBirthday());
            // 患者类型
            paramMap.put(PAT_TYPE, lisApplyInfo.getPatType());
            // 联系方式
            paramMap.put(PAT_PHONE, peRegister.getTelphone());
            // 患者科室id
            paramMap.put(PAT_DEPT_ID, StaticValue.DEPT_ID.getCode());
            // 患者科室名称
            paramMap.put(PAT_DEPT_NAME, StaticValue.DEPT_NAME.getCode());
            // 申请医生id
            paramMap.put(REQ_DOC_ID, StaticValue.DOC_ID.getCode());
            // 申请医生名称
            paramMap.put(REQ_DOC_NAME, StaticValue.DOC_NAME.getCode());
            // 组装项目参数
            List<Map<String, Object>> itemList = new ArrayList<>();
            // 条码表
            List<LisApplyBarCodeReportId> LisApplyBarCodeReportId = new ArrayList<>();
            // 循环查询出来的项目组装详情参数
            for (PatItems patItem : patItems) {
                // 创建参数map
                Map<String, Object> itemMap = new HashMap<>();
                // 根据查询出来的项目，对应获取项目列表里面的项目详情信息
                List<CheckProject> ItemInfo = checkProjectService.list(new LambdaQueryWrapper<CheckProject>().eq(CheckProject::getMineProjectNo, OtherUtil.strControll(patItem.getComposeItemNo())));
                if (CollUtil.isEmpty(ItemInfo)) {
                    log.log("根据本地数据库项目号：" + patItem.getCureNo() + "项目名称：" + patItem.getItemName() + "没有查询到，相应的项目详情数据，请到：”检验项目配置“ 进行配置！");
                    result = log.resultLog("根据本地数据库项目号：" + patItem.getCureNo() + "项目名称：" + patItem.getItemName() + "没有查询到，相应的项目详情数据，请到：”检验项目配置“ 进行配置！");
                    continue;
                }
                // 项目id
                itemMap.put(LAB_ITEM_ID, ItemInfo.get(0).getLabItemId());
                // 检验项目业务号--传输 cur_assay_master.cure_no字段
                itemMap.put(MAIN_ORDER_ID, patItem.getCureNo());
                // 备注
                itemMap.put(NOTE, StaticValue.WU.getCode());
                // 医嘱字典id
                itemMap.put(ORDER_ID, ItemInfo.get(0).getOrderId());
                // 医嘱字典名称
                itemMap.put(ORDER_NAME, ItemInfo.get(0).getOrderName());
                // 价格
                itemMap.put(PRICE, StaticValue.ZERO.getCode());
                // 样本id
                itemMap.put(SAMPLE_CLASS_ID, ItemInfo.get(0).getSampleClassId());
                // 样本名称
                itemMap.put(SAMPLE_CLASS_NAME, ItemInfo.get(0).getSampleClassName());
                // 报告项目id
                itemMap.put(REPORT_ITEM_ID, ItemInfo.get(0).getReportItemId());
                // 执行状态，0 未缴费:1已缴费:2已退费:3:申请退费(注:不需要在 HIS 缴费传值为1)
                itemMap.put(EXECUTE_STATE, StaticValue.ONE.getCode());
                // 添加到详细list中
                itemList.add(itemMap);

                // 组装条码信息报告信息表
                LisApplyBarCodeReportId lisApplyBarCodeReportId = new LisApplyBarCodeReportId();
                lisApplyBarCodeReportId.setPatientNo(peRegister.getPatientNo());
                lisApplyBarCodeReportId.setPatientName(peRegister.getPatientName());
                lisApplyBarCodeReportId.setPatId(peRegister.getPatId());
                lisApplyBarCodeReportId.setLabItemId(ItemInfo.get(0).getLabItemId());
                lisApplyBarCodeReportId.setLabItemName(ItemInfo.get(0).getLabName());
                lisApplyBarCodeReportId.setItemNo(patItem.getComposeItemNo());
                LisApplyBarCodeReportId.add(lisApplyBarCodeReportId);
            }
            // 数据持久化
            lisApplyBarCodeReportIdService.saveBatch(LisApplyBarCodeReportId);
            // 封装参数
            paramMap.put(ORDER_ITEM_LIST, itemList);

            // 请求信息封装
            log.setSendMessage(JSONUtil.parse(paramMap).toString());
            // 发送请求
//                String res = RequestUtil.go(LIS_APPLY.getUrl(), LIS_APPLY.getRequestType(), paramMap);
            // 假数据
            String res = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"decorate\": true,\n" +
                    "    \"code\": \"0000\",\n" +
                    "    \"message\": \"成功\",\n" +
                    "    \"traceId\": \"e6b21007-e2fc-41e0-86b9-55f3e2a06c8b\",\n" +
                    "    \"exceptionName\": null,\n" +
                    "    \"qualityMonitor\": null,\n" +
                    "    \"ignoreQualityMonitor\": false,\n" +
                    "    \"level\": \"info\",\n" +
                    "    \"service\": \"msun-middle-business-lis\",\n" +
                    "    \"businessException\": false\n" +
                    "}";
            // 返回信息封装
            log.setReceiveMessage(res);
            // 判断
            if (!JSONUtil.isJson(res)) {
                throw new RuntimeException("返回信息不是json！");
            }
            // 将返回的数据转换成为，接收类数据
            BaseResponseEntity response = JSONUtil.toBean(res, BaseResponseEntity.class);
            // 请求不成功
            if (!response.getSuccess()) {
                throw new RuntimeException("LIS检验申请提交失败！");
            } else {
                // 将患者类型维护
                peRegister.setPatType(lisApplyInfo.getPatType());
                // 已经提交检验申请,1:是，0 否
                peRegister.setIsLisApply(StaticValue.ONE.getCode());
                // 数据持久化
                this.updateById(peRegister);

                log.success(true);
                log.log("LIS检验申请提交成功！");
                result = log.resultLog("LIS检验申请提交成功！");
            }
        } catch (Exception e) {
            log.success(false);
            log.log("报错：" + e.getMessage());
            result = log.resultLog("报错：" + e.getMessage());
        } finally {
            // 保存日志
            log.saveLog();
        }
        // 添加人员返回信息
        resultAll.append(result);
    }

    /**
     * @description: 条码生成
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/18/23 11:04 AM
     */
    @Override
    @Transactional
    public Result barCodeBuild(List<String> ids) {
        // 结果返回信息
        StringBuffer resultAll = new StringBuffer();
        // 查询用户信息
        List<PeRegisterList> peRegisterLists = this.listByIds(ids);
        // 循环数据操作
        for (PeRegisterList peRegister : peRegisterLists) {
            barCodeBuildOne(resultAll, peRegister);
        }
        return Result.ok(resultAll);
    }

    private void barCodeBuildOne(StringBuffer resultAll, PeRegisterList peRegister) {
        // 单个人员返信息
        StringBuffer result;
        // 日志信息
        LogUtilNew log = LogUtilNew.getInstance(BAR_CODE_BUILD, peRegister);
        try {
            // 组装参数
            Map<String, Object> paramMap = new HashMap<>();
            // 患者id
            paramMap.put(PAT_ID, peRegister.getPatId());
            // 患者类型
            paramMap.put(PAT_TYPE, peRegister.getPatType());
            // 检验项目业务编号list
            List<String> mainOrderIdList = new ArrayList<>();
            // 获取用户检验项目
            List<PatItems> patItems = peRegisterListMapper.getPatItems(peRegister.getPatientNo());
            for (PatItems patItem : patItems) {
                mainOrderIdList.add(patItem.getCureNo());
            }
            paramMap.put(MAIN_ORDER_ID_LIST, mainOrderIdList);
            // 放发送的请求
            log.setSendMessage(JSONUtil.parse(paramMap).toString());
            // 发送请求
//                String res = RequestUtil.go(BAR_CODE_BUILD.getUrl(), BAR_CODE_BUILD.getRequestType(), paramMap);
            String res = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"decorate\": true,\n" +
                    "    \"code\": \"0000\",\n" +
                    "    \"message\": \"成功\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"barcode\": \"2310051056\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patSex\": \"男\",\n" +
                    "            \"patAge\": \"33岁\",\n" +
                    "            \"tubeColor\": \"浅红色\",\n" +
                    "            \"createTime\": \"2023-10-05 11:18:15\",\n" +
                    "            \"sampleClassName\": \"血清\",\n" +
                    "            \"labItemName\": [\n" +
                    "                \"社区查体生化项目\"\n" +
                    "            ],\n" +
                    "            \"labItemIdList\": [\n" +
                    "                \"78016\"\n" +
                    "            ],\n" +
                    "            \"patNo\": \"2207011426\",\n" +
                    "            \"patId\": \"2207011426\",\n" +
                    "            \"receiptText\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"barcode\": \"2310051055\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patSex\": \"男\",\n" +
                    "            \"patAge\": \"33岁\",\n" +
                    "            \"tubeColor\": \"浅红色\",\n" +
                    "            \"createTime\": \"2023-10-05 11:18:15\",\n" +
                    "            \"sampleClassName\": \"血清\",\n" +
                    "            \"labItemName\": [\n" +
                    "                \"社区查体生化项目\"\n" +
                    "            ],\n" +
                    "            \"labItemIdList\": [\n" +
                    "                \"1506431\"\n" +
                    "            ],\n" +
                    "            \"patNo\": \"2207011426\",\n" +
                    "            \"patId\": \"2207011426\",\n" +
                    "            \"receiptText\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"barcode\": \"2310051054\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patSex\": \"男\",\n" +
                    "            \"patAge\": \"33岁\",\n" +
                    "            \"tubeColor\": \"浅红色\",\n" +
                    "            \"createTime\": \"2023-10-05 11:18:15\",\n" +
                    "            \"sampleClassName\": \"血清\",\n" +
                    "            \"labItemName\": [\n" +
                    "                \"社区查体生化项目\"\n" +
                    "            ],\n" +
                    "            \"labItemIdList\": [\n" +
                    "                \"1721009\"\n" +
                    "            ],\n" +
                    "            \"patNo\": \"2207011426\",\n" +
                    "            \"patId\": \"2207011426\",\n" +
                    "            \"receiptText\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"barcode\": \"2310051053\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patSex\": \"男\",\n" +
                    "            \"patAge\": \"33岁\",\n" +
                    "            \"tubeColor\": \"浅红色\",\n" +
                    "            \"createTime\": \"2023-10-05 11:18:15\",\n" +
                    "            \"sampleClassName\": \"血清\",\n" +
                    "            \"labItemName\": [\n" +
                    "                \"社区查体生化项目\"\n" +
                    "            ],\n" +
                    "            \"labItemIdList\": [\n" +
                    "                \"77834\"\n" +
                    "            ],\n" +
                    "            \"patNo\": \"2207011426\",\n" +
                    "            \"patId\": \"2207011426\",\n" +
                    "            \"receiptText\": null\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"traceId\": \"e6b21007-e2fc-41e0-86b9-55f3e2a06c8b\",\n" +
                    "    \"exceptionName\": null,\n" +
                    "    \"qualityMonitor\": null,\n" +
                    "    \"ignoreQualityMonitor\": false,\n" +
                    "    \"level\": \"info\",\n" +
                    "    \"service\": \"msun-middle-business-lis\",\n" +
                    "    \"businessException\": false\n" +
                    "}";
            // 存放接收数据
            log.setReceiveMessage(res);
            // 判断
            if (!JSONUtil.isJson(res)) {
                throw new RuntimeException("结果返回的不是json数据！");
            }
            // 接受类进行接受
            BarCodeBuildResponse response = JSONUtil.toBean(res, BarCodeBuildResponse.class);
            if (BeanUtil.isEmpty(response) || CollUtil.isEmpty(response.getData())) {
                throw new RuntimeException("接收类接收为空！");
            }
            // 将条码信息进行维护
            for (BarCodeBuildVo barCode : response.getData()) {
                // 循环 项目labItemId
                if (CollUtil.isEmpty(barCode.getLabItemIdList())) {
                    log.log("labItemName为空");
                    result = log.resultLog("labItemName为空");
                } else {
                    for (String labItemId : barCode.getLabItemIdList()) {
                   /*     // 先查询项目维护信息
                        List<CheckProject> list = checkProjectService.list(new LambdaQueryWrapper<CheckProject>().eq(CheckProject::getLabItemId, labItemId));
                        if (CollUtil.isEmpty(list)) {
                            log.log("根据条码生成接口返回信息来的项目号：" + labItemId + "查询项目为空！");
                            result = log.resultLog("根据条码生成接口返回信息来的项目号：" + labItemId + "查询项目为空！");
                        } else {
                            List<PatItems> patItem = peRegisterListMapper.getPatItem(peRegister.getPatientNo(), list.get(0).getMineItemRemork());
                            if (CollUtil.isEmpty(patItem)) {
                                log.log("根据条码生成接口返回信息来的项目号：" + labItemId + "项目本地号："+ list.get(0).getMineItemRemork() +"查询用户具体的项目结果返回为空！");
                                result = log.resultLog("根据条码生成接口返回信息来的项目号：" + labItemId + "项目本地号："+ list.get(0).getMineItemRemork() +"查询用户具体的项目结果返回为空！");
                            }else{
                                // 维护条码号
//                                    curAssayMasterService.updateById(new CurAssayMaster().setAssayNo(patItem.get(0).getCureNo()).setBarCode(barCode.getBarcode()));
                            }
                        }*/
                        // 维护到新的表格中
                        List<LisApplyBarCodeReportId> list = lisApplyBarCodeReportIdService.list(new LambdaQueryWrapper<LisApplyBarCodeReportId>().eq(LisApplyBarCodeReportId::getPatientNo, peRegister.getPatientNo()).eq(LisApplyBarCodeReportId::getLabItemId, labItemId));
                        log.log("根据条码接口返回的项目id（labItenId）找到的LIS检验申请信息：" + list);
                        if (CollUtil.isEmpty(list)) {
                            log.log("根据条码生成接口返回信息来的项目号：" + labItemId + "查询项目为空！");
                            result = log.resultLog("根据条码生成接口返回信息来的项目号：" + labItemId + "查询项目为空！");
                        } else {
                            // 维护条码信息
                            list.get(0).setBarCode(barCode.getBarcode());
                            // 数据持久化
                            lisApplyBarCodeReportIdService.updateById(list.get(0));
                        }
                    }
                }
            }
            // 维护条码生成成功 1: 成功 0:失败
            peRegister.setIsBarCodeBuild(StaticValue.ONE.getCode());
            // 数据持久化
            this.updateById(peRegister);
            // 维护返回信息
            log.success(true);
            log.log("条码信息生成成功！");
            result = log.resultLog("条码信息生成成功！");
        } catch (Exception e) {
            log.success(false);
            log.log("报错：" + e.getMessage());
            result = log.resultLog("报错：" + e.getMessage());
        } finally {
            // 保存日志
            log.saveLog();
        }
        // 将单个信息添加到中返回信息中
        resultAll.append(result);
    }

    /**
     * @description: 报告查询维护
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 2023/10/18 22:17
     */
    @Override
    public Result reportSearch(List<String> ids) {
        // 信息总返回
        StringBuffer resultAll = new StringBuffer();
        // 查询人员信息
        List<PeRegisterList> peRegisterLists = this.listByIds(ids);
        // 获取所有的主表项目和子项目checkProject表中获取
        List<CheckProject> checkProjectList = checkProjectService.list();
        List<CheckProjectDetail> checkProjectDetailList = checkProjectDetailService.list();
        // 循环数据进行操作
        for (PeRegisterList peRegister : peRegisterLists) {
            try {
                // 报告ID查询和维护
                resultAll.append(reportIdSearch(peRegister));
                // 报告内容查询和维护
                resultAll.append(reportDetailSearch(peRegister, checkProjectList, checkProjectDetailList));
            } catch (Exception e) {
                resultAll.append("患者：" + peRegister.getPatientName() + e.getMessage());
            }
        }
        return Result.ok(resultAll);
    }

    /**
     * @description: 多重操作
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 2023/10/20 20:20
     */
    @Override
    public Result buttonAll(LISApplyInfo lisApplyInfo) {
        StringBuffer resultAll = new StringBuffer();
        List<PeRegisterList> peRegisterLists = this.listByIds(lisApplyInfo.getPatIds());
        // 循环数据
        for (PeRegisterList peRegister : peRegisterLists) {
            String step = this.calculPersonStep(peRegister);
            switch (step) {
                case "1":
                    // 生成patId
                    personMake(resultAll, peRegister);
                    // 判断是否成功生成patId
                    PeRegisterList byId = this.getById(peRegister.getId());
                    if (StrUtil.isEmpty(byId.getPatId())) {
                        // 重新生成parId
                        personMake(resultAll, peRegister);
                    }
                    // 生成LIS检验申请
                    LISApplyOne(lisApplyInfo, resultAll, peRegister);
                    // 生成条码
                    barCodeBuildOne(resultAll, peRegister);
                    break;
                case "2":
                    // 生成LIS检验申请
                    LISApplyOne(lisApplyInfo, resultAll, peRegister);
                    // 生成条码
                    barCodeBuildOne(resultAll, peRegister);
                    break;
                case "3":
                    // 生成条码
                    barCodeBuildOne(resultAll, peRegister);
                    break;
                default:
                    break;
            }
        }
        return Result.ok(resultAll);
    }

    private String calculPersonStep(PeRegisterList peRegister) {
        // 判断有没有维护patId 是否需要人员查询，和创建档案
        if (StrUtil.isEmpty(peRegister.getPatId())) {
            return StaticValue.ONE.getCode();
        } else if (!StaticValue.ONE.getCode().equals(peRegister.getIsLisApply())) {
            return StaticValue.TWO.getCode();
        } else if (!StaticValue.ONE.getCode().equals(peRegister.getIsBarCodeBuild())) {
            return StaticValue.THREE.getCode();
        } else {
            return StaticValue.ZERO.getCode();
        }
    }

    private void personMake(StringBuffer resultAll, PeRegisterList peRegister) {
        // 人员查询
        personSearchOne(resultAll, peRegister);
        // 查询人员是否维护了patid没有的话那就创建档案
        PeRegisterList one = this.getOne(new LambdaQueryWrapper<PeRegisterList>().eq(PeRegisterList::getId, peRegister.getId()));
        // 判断有没有查询过
        if (StrUtil.isEmpty(one.getPatId())) {
            // 生成信息
            personCreateOne(resultAll, peRegister);
        }
    }

    /**
     * @description: 报告ID查询和维护
     * @param: peRegister
     * @return: java.lang.StringBuffer
     * @author lhr
     * @date: 2023/10/18 23:55
     */
    @Transactional
    public StringBuffer reportIdSearch(PeRegisterList peRegister) throws Exception {
        StringBuffer result = null;
        LogUtilNew log = LogUtilNew.getInstance(REPORT_ID_SEARCH, peRegister);
        try {
            // 获取该患者的条码关系表数据
            List<LisApplyBarCodeReportId> list = lisApplyBarCodeReportIdService.list(new LambdaQueryWrapper<LisApplyBarCodeReportId>().eq(LisApplyBarCodeReportId::getPatientNo, peRegister.getPatientNo()));
            // 维护日志
            log.log("查询该患者的条码关系表数据：" + JSONUtil.parse(list));
            // 判断
            if (CollUtil.isEmpty(list)) {
                throw new RuntimeException("没有查询到该用户的条码关系");
            }
            // 组装参数，获取条码信息
            Map<String, Object> paramsMap = new HashMap<>();
            // 时间范围为，从该患者提交LIS检验申请开始，一直到今天
            // 开始时间
            paramsMap.put(START_TIME, DateUtil.format(list.get(0).getCreateTime(), NORM_DATETIME_FORMAT));
            // 结束时间
            paramsMap.put(END_TIME, DateUtil.now());
            // 患者id
            paramsMap.put(PAT_ID, peRegister.getPatId());
            // 检验好，体检号
            paramsMap.put(PAT_NO, peRegister.getPatientNo());
            // 条码号
            List<String> barcodeList = new ArrayList<>();
            for (LisApplyBarCodeReportId lisApplyBarCodeReportId : list) {
                if (StrUtil.isNotEmpty(lisApplyBarCodeReportId.getBarCode())) {
                    barcodeList.add(lisApplyBarCodeReportId.getBarCode());
                }
            }
            // 判断筛选后的条码信息是否为空
            if (CollUtil.isEmpty(barcodeList)) {
                throw new RuntimeException("条码参数为空，条码关系表，并没有维护条码信息");
            }
            paramsMap.put(BAR_CODE_LIST, barcodeList);
            // 发送参数
            log.setSendMessage(JSONUtil.parse(paramsMap).toString());
            // 发送请求
//            String res = RequestUtil.go(REPORT_ID_SEARCH.getUrl(), REPORT_ID_SEARCH.getRequestType(), paramsMap);
            String res = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"decorate\": true,\n" +
                    "    \"code\": \"0000\",\n" +
                    "    \"message\": \"成功\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"reportId\": \"20231006023853001\",\n" +
                    "            \"keynoGroup\": \"20231006023853001\",\n" +
                    "            \"patId\": \"2156980\",\n" +
                    "            \"barCode\": \"2310051054\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patDeptName\": \"社区门诊\",\n" +
                    "            \"patInHosId\": null,\n" +
                    "            \"patType\": \"3\",\n" +
                    "            \"collectTime\": \"2023-10-05 17:04:29\",\n" +
                    "            \"reportTime\": \"2023-10-06 09:00:20\",\n" +
                    "            \"flagGerm\": 0,\n" +
                    "            \"reportNotes\": \"\",\n" +
                    "            \"testList\": \"尿液分析\",\n" +
                    "            \"testUser\": \"郑伟阶\",\n" +
                    "            \"checkUser\": \"李万生\",\n" +
                    "            \"filingMemo\": null,\n" +
                    "            \"smearMemo\": null,\n" +
                    "            \"criticalFlag\": 0,\n" +
                    "            \"multiDrugResistance\": 0,\n" +
                    "            \"sampleClassId\": \"20000113\",\n" +
                    "            \"sampleClassName\": \"尿液\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"reportId\": \"20231006040853001\",\n" +
                    "            \"keynoGroup\": \"20231006040853001\",\n" +
                    "            \"patId\": \"2156980\",\n" +
                    "            \"barCode\": \"2310051056\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patDeptName\": \"社区门诊\",\n" +
                    "            \"patInHosId\": null,\n" +
                    "            \"patType\": \"3\",\n" +
                    "            \"collectTime\": \"2023-10-05 17:04:29\",\n" +
                    "            \"reportTime\": \"2023-10-06 09:02:24\",\n" +
                    "            \"flagGerm\": 0,\n" +
                    "            \"reportNotes\": \"\",\n" +
                    "            \"testList\": \"葡萄糖测定(化学法),社区查体生化项目\",\n" +
                    "            \"testUser\": \"陈柯\",\n" +
                    "            \"checkUser\": \"刘涛\",\n" +
                    "            \"filingMemo\": null,\n" +
                    "            \"smearMemo\": null,\n" +
                    "            \"criticalFlag\": 0,\n" +
                    "            \"multiDrugResistance\": 0,\n" +
                    "            \"sampleClassId\": \"20000207\",\n" +
                    "            \"sampleClassName\": \"血清\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"reportId\": \"20231006040853001\",\n" +
                    "            \"keynoGroup\": \"20231006040853001\",\n" +
                    "            \"patId\": \"2156980\",\n" +
                    "            \"barCode\": \"2310051055\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patDeptName\": \"社区门诊\",\n" +
                    "            \"patInHosId\": null,\n" +
                    "            \"patType\": \"3\",\n" +
                    "            \"collectTime\": \"2023-10-05 17:04:29\",\n" +
                    "            \"reportTime\": \"2023-10-06 09:02:24\",\n" +
                    "            \"flagGerm\": 0,\n" +
                    "            \"reportNotes\": \"\",\n" +
                    "            \"testList\": \"葡萄糖测定(化学法),社区查体生化项目\",\n" +
                    "            \"testUser\": \"陈柯\",\n" +
                    "            \"checkUser\": \"刘涛\",\n" +
                    "            \"filingMemo\": null,\n" +
                    "            \"smearMemo\": null,\n" +
                    "            \"criticalFlag\": 0,\n" +
                    "            \"multiDrugResistance\": 0,\n" +
                    "            \"sampleClassId\": \"20000207\",\n" +
                    "            \"sampleClassName\": \"血清\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"reportId\": \"20231006095353001\",\n" +
                    "            \"keynoGroup\": \"20231006095353001\",\n" +
                    "            \"patId\": \"2156980\",\n" +
                    "            \"barCode\": \"2310051053\",\n" +
                    "            \"patName\": \"22\",\n" +
                    "            \"patDeptName\": \"社区门诊\",\n" +
                    "            \"patInHosId\": null,\n" +
                    "            \"patType\": \"3\",\n" +
                    "            \"collectTime\": \"2023-10-05 17:04:29\",\n" +
                    "            \"reportTime\": \"2023-10-06 09:02:24\",\n" +
                    "            \"flagGerm\": 0,\n" +
                    "            \"reportNotes\": \"\",\n" +
                    "            \"testList\": \"全血细胞计数+五分类\",\n" +
                    "            \"testUser\": \"陈柯\",\n" +
                    "            \"checkUser\": \"刘涛\",\n" +
                    "            \"filingMemo\": null,\n" +
                    "            \"smearMemo\": null,\n" +
                    "            \"criticalFlag\": 0,\n" +
                    "            \"multiDrugResistance\": 0,\n" +
                    "            \"sampleClassId\": \"20000207\",\n" +
                    "            \"sampleClassName\": \"血清\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"traceId\": \"9067ceab-f117-4c1a-b315-6ba12456ac00\",\n" +
                    "    \"exceptionName\": null,\n" +
                    "    \"qualityMonitor\": null,\n" +
                    "    \"ignoreQualityMonitor\": false,\n" +
                    "    \"level\": \"info\",\n" +
                    "    \"service\": \"msun-middle-business-lis\",\n" +
                    "    \"businessException\": false\n" +
                    "}";
            // 接受参数
            log.setReceiveMessage(res);
            // 判断
            if (!JSONUtil.isTypeJSON(res)) {
                throw new RuntimeException("返回数据不是json");
            }
            // 映射接收类
            ReportIdSearchResponse response = JSONUtil.toBean(res, ReportIdSearchResponse.class);
            // 判断
            if (BeanUtil.isEmpty(response) || CollUtil.isEmpty(response.getData()) || (!response.getSuccess())) {
                throw new RuntimeException("接口返回信息为空，或者失败！");
            }
            // 维护报告ID
            for (ReportIdSearchVo datum : response.getData()) {
                // 根据患者id和条码号更新
                boolean update = lisApplyBarCodeReportIdService.update(new LambdaUpdateWrapper<LisApplyBarCodeReportId>()
                        .eq(LisApplyBarCodeReportId::getPatId, datum.getPatId())
                        .eq(LisApplyBarCodeReportId::getBarCode, datum.getBarCode())
                        .set(LisApplyBarCodeReportId::getReportId, datum.getReportId()));
                log.log("更新报告id：患者id：" + datum.getPatId() + ",条码号：" + datum.getBarCode() + ",报告id：" + datum.getReportId() + "||");
            }
            // 返回成功信息
            log.success(true);
            log.log("报告id查询维护成功！");
            result = log.resultLog("报告id查询维护成功！");
        } catch (Exception e) {
            // 接口请求失败
            log.success(false);
            // 日志记录
            log.log("报错：" + e.getMessage());
            result = log.resultLog("报错：" + e.getMessage());
            // 抛出异常进行下一位患者。
            throw e;
        } finally {
            // 保存日志
            log.saveLog();
        }
        return result;
    }

    /**
     * @description: 报告内容查询和维护
     * @param: peRegister
     * @return: java.lang.StringBuffer
     * @author lhr
     * @date: 2023/10/18 23:55
     */
    @Transactional
    public StringBuffer reportDetailSearch(PeRegisterList peRegister, List<CheckProject> checkProjectList, List<CheckProjectDetail> checkProjectDetailList) throws Exception {
        StringBuffer result = new StringBuffer("报告详情维护失败！");
        LogUtilNew log = LogUtilNew.getInstance(REPORT_DETAIL_SEARCH, peRegister);
        try {
            // 组装参数，获取报告id
            Map<String, Object> paramsMap = new HashMap<>();
            // 获取报告单id，根据条码关系表维护的id查询
            List<LisApplyBarCodeReportId> list = lisApplyBarCodeReportIdService.list(new LambdaQueryWrapper<LisApplyBarCodeReportId>().eq(LisApplyBarCodeReportId::getPatientNo, peRegister.getPatientNo()));
            // 判断
            if (CollUtil.isEmpty(list)) {
                throw new RuntimeException("查询患者的报告ID为空，无法生成报告！");
            }
            // 创建变量并循环赋值
            StringBuffer reportId = new StringBuffer();
            // 赋值
            for (LisApplyBarCodeReportId lisApplyBarCodeReportId : list) {
                if (StrUtil.isNotEmpty(lisApplyBarCodeReportId.getReportId())) {
                    reportId.append(lisApplyBarCodeReportId.getReportId() + ",");
                }
            }
            // 报告单id
            paramsMap.put(REPORT_ID, reportId.toString());
            // 记录日志
            log.setSendMessage(JSONUtil.parse(paramsMap).toString());
            // 发送请求
//            String res = RequestUtil.go(REPORT_DETAIL_SEARCH.getUrl(), REPORT_DETAIL_SEARCH.getRequestType(), paramsMap);
            // 假数据
            String res = "{\n" +
                    "  \"success\" : true,\n" +
                    "  \"decorate\" : true,\n" +
                    "  \"code\" : \"0000\",\n" +
                    "  \"message\" : \"成功\",\n" +
                    "  \"data\" : [ {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357213282306\",\n" +
                    "    \"itemId\" : \"20000364\",\n" +
                    "    \"itemNo\" : \"GLU（U）\",\n" +
                    "    \"itemName\" : \"葡萄糖\",\n" +
                    "    \"itemEname\" : \"GLU\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 1,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : 1,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:27:43\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608903\",\n" +
                    "    \"itemId\" : \"20000799\",\n" +
                    "    \"itemNo\" : \"URO\",\n" +
                    "    \"itemName\" : \"尿胆原\",\n" +
                    "    \"itemEname\" : \"URO\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"umol/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 3,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:27:43\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608905\",\n" +
                    "    \"itemId\" : \"20000167\",\n" +
                    "    \"itemNo\" : \"BIL\",\n" +
                    "    \"itemName\" : \"胆红素\",\n" +
                    "    \"itemEname\" : \"BIL\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"umol/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 4,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:27:47\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608907\",\n" +
                    "    \"itemId\" : \"20000510\",\n" +
                    "    \"itemNo\" : \"KET\",\n" +
                    "    \"itemName\" : \"酮体\",\n" +
                    "    \"itemEname\" : \"KET\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"mg/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 5,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:27:48\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608910\",\n" +
                    "    \"itemId\" : \"20000717\",\n" +
                    "    \"itemNo\" : \"SG\",\n" +
                    "    \"itemName\" : \"比重\",\n" +
                    "    \"itemEname\" : \"SG\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"\",\n" +
                    "    \"itemRange\" : \"1.015~1.025\",\n" +
                    "    \"itemRangeHigh\" : \"1.025\",\n" +
                    "    \"itemRangeLow\" : \"1.015\",\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"1.016\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 6,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:27:52\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608914\",\n" +
                    "    \"itemId\" : \"20000168\",\n" +
                    "    \"itemNo\" : \"BLD\",\n" +
                    "    \"itemName\" : \"尿潜血\",\n" +
                    "    \"itemEname\" : \"BLD\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"mg/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 8,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:27:54\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608919\",\n" +
                    "    \"itemId\" : \"20000637\",\n" +
                    "    \"itemNo\" : \"PH\",\n" +
                    "    \"itemName\" : \"酸碱度\",\n" +
                    "    \"itemEname\" : \"PH\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"\",\n" +
                    "    \"itemRange\" : \"5.4~8.0\",\n" +
                    "    \"itemRangeHigh\" : \"8.0\",\n" +
                    "    \"itemRangeLow\" : \"5.4\",\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"7.2\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 10,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:28:00\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357414608923\",\n" +
                    "    \"itemId\" : \"20000657\",\n" +
                    "    \"itemNo\" : \"PRO\",\n" +
                    "    \"itemName\" : \"蛋白质\",\n" +
                    "    \"itemEname\" : \"PRO\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"g/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 11,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:28:04\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357448163333\",\n" +
                    "    \"itemId\" : \"20000598\",\n" +
                    "    \"itemNo\" : \"NIT\",\n" +
                    "    \"itemName\" : \"亚硝酸盐\",\n" +
                    "    \"itemEname\" : \"NIT\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 12,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:28:05\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357448163337\",\n" +
                    "    \"itemId\" : \"20000537\",\n" +
                    "    \"itemNo\" : \"LEU\",\n" +
                    "    \"itemName\" : \"白细胞\",\n" +
                    "    \"itemEname\" : \"LEU\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"Leu/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 13,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:28:07\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357448163341\",\n" +
                    "    \"itemId\" : \"20000803\",\n" +
                    "    \"itemNo\" : \"VC\",\n" +
                    "    \"itemName\" : \"维生素C\",\n" +
                    "    \"itemEname\" : \"VC\",\n" +
                    "    \"itemRangeType\" : \"6\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"阴性\",\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"-\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 14,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:28:09\",\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357448163345\",\n" +
                    "    \"itemId\" : \"20000203\",\n" +
                    "    \"itemNo\" : \"cbxb\",\n" +
                    "    \"itemName\" : \"镜检白细胞\",\n" +
                    "    \"itemEname\" : \"cbxb\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"个/HP\",\n" +
                    "    \"itemRange\" : \"0~5\",\n" +
                    "    \"itemRangeHigh\" : \"5\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"未见\",\n" +
                    "    \"mark\" : null,\n" +
                    "    \"sno\" : 15,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : null,\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357448163349\",\n" +
                    "    \"itemId\" : \"20000255\",\n" +
                    "    \"itemNo\" : \"chxb\",\n" +
                    "    \"itemName\" : \"镜检红细胞\",\n" +
                    "    \"itemEname\" : \"chxb\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"个/HP\",\n" +
                    "    \"itemRange\" : \"0~5\",\n" +
                    "    \"itemRangeHigh\" : \"5\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"未见\",\n" +
                    "    \"mark\" : null,\n" +
                    "    \"sno\" : 16,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : null,\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006023853001\",\n" +
                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
                    "    \"rptDetailId\" : \"6100734357448163353\",\n" +
                    "    \"itemId\" : \"20000368\",\n" +
                    "    \"itemNo\" : \"gx\",\n" +
                    "    \"itemName\" : \"管型\",\n" +
                    "    \"itemEname\" : \"gx\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"个/LP\",\n" +
                    "    \"itemRange\" : \"0~0\",\n" +
                    "    \"itemRangeHigh\" : \"0\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1721009\",\n" +
                    "    \"itemResult\" : \"0\",\n" +
                    "    \"mark\" : null,\n" +
                    "    \"sno\" : 17,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : null,\n" +
                    "    \"sampleClassId\" : \"20000113\",\n" +
                    "    \"sampleClassName\" : \"尿液\"\n" +
                    "  }," +
//                    " {\n" +
//                    "    \"reportId\" : \"20231006023853001\",\n" +
//                    "    \"keynoGroup\" : \"20231006023853001\",\n" +
//                    "    \"rptDetailId\" : \"6100734357448163357\",\n" +
//                    "    \"itemId\" : \"20001036\",\n" +
//                    "    \"itemNo\" : \"尿液上皮细胞\",\n" +
//                    "    \"itemName\" : \"上皮细胞\",\n" +
//                    "    \"itemEname\" : \"尿液上皮细胞\",\n" +
//                    "    \"itemRangeType\" : \"6\",\n" +
//                    "    \"unit\" : \"\",\n" +
//                    "    \"itemRange\" : \"阴性\",\n" +
//                    "    \"itemRangeHigh\" : null,\n" +
//                    "    \"itemRangeLow\" : null,\n" +
//                    "    \"labItemNo\" : \"1721009\",\n" +
//                    "    \"itemResult\" : \"-\",\n" +
//                    "    \"mark\" : \"\",\n" +
//                    "    \"sno\" : 18,\n" +
//                    "    \"germNo\" : null,\n" +
//                    "    \"germName\" : null,\n" +
//                    "    \"yinYangNature\" : null,\n" +
//                    "    \"expertOpinion\" : null,\n" +
//                    "    \"reportNotes\" : null,\n" +
//                    "    \"filingMemo\" : null,\n" +
//                    "    \"smearMemo\" : null,\n" +
//                    "    \"pageType\" : null,\n" +
//                    "    \"antList\" : null,\n" +
//                    "    \"flagCritical\" : 0,\n" +
//                    "    \"collectTime\" : null,\n" +
//                    "    \"resultTime\" : \"2023-10-06 08:28:16\",\n" +
//                    "    \"sampleClassId\" : \"20000113\",\n" +
//                    "    \"sampleClassName\" : \"尿液\"\n" +
//                    "  }, " +
                    "{\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893487804420\",\n" +
                    "    \"itemId\" : \"20000130\",\n" +
                    "    \"itemNo\" : \"ALT\",\n" +
                    "    \"itemName\" : \"丙氨酸氨基转移酶\",\n" +
                    "    \"itemEname\" : \"ALT\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"U/L\",\n" +
                    "    \"itemRange\" : \"0~50\",\n" +
                    "    \"itemRangeHigh\" : \"50\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"32.50\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 1,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:37:28\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131011\",\n" +
                    "    \"itemId\" : \"20000156\",\n" +
                    "    \"itemNo\" : \"AST\",\n" +
                    "    \"itemName\" : \"天门冬氨酸氨基转移酶\",\n" +
                    "    \"itemEname\" : \"AST\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"U/L\",\n" +
                    "    \"itemRange\" : \"0~40\",\n" +
                    "    \"itemRangeHigh\" : \"40\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"29.80\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 2,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:37:36\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131014\",\n" +
                    "    \"itemId\" : \"20000753\",\n" +
                    "    \"itemNo\" : \"TBIL\",\n" +
                    "    \"itemName\" : \"总胆红素\",\n" +
                    "    \"itemEname\" : \"TBIL\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"umol/L\",\n" +
                    "    \"itemRange\" : \"0~20.0\",\n" +
                    "    \"itemRangeHigh\" : \"20.0\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"16.20\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 3,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:37:40\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131018\",\n" +
                    "    \"itemId\" : \"20000299\",\n" +
                    "    \"itemNo\" : \"DBIL\",\n" +
                    "    \"itemName\" : \"直接胆红素\",\n" +
                    "    \"itemEname\" : \"DBIL\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"umol/L\",\n" +
                    "    \"itemRange\" : \"0~7.0\",\n" +
                    "    \"itemRangeHigh\" : \"7.0\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"3.90\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 4,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:37:46\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131021\",\n" +
                    "    \"itemId\" : \"20000774\",\n" +
                    "    \"itemNo\" : \"TP\",\n" +
                    "    \"itemName\" : \"总蛋白\",\n" +
                    "    \"itemEname\" : \"TP\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"g/L\",\n" +
                    "    \"itemRange\" : \"65~85\",\n" +
                    "    \"itemRangeHigh\" : \"85\",\n" +
                    "    \"itemRangeLow\" : \"65\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"80.20\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 5,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:37:54\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131025\",\n" +
                    "    \"itemId\" : \"20000126\",\n" +
                    "    \"itemNo\" : \"ALB\",\n" +
                    "    \"itemName\" : \"白蛋白\",\n" +
                    "    \"itemEname\" : \"ALB\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"g/L\",\n" +
                    "    \"itemRange\" : \"40~55\",\n" +
                    "    \"itemRangeHigh\" : \"55\",\n" +
                    "    \"itemRangeLow\" : \"40\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"49.00\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 6,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:37:56\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131030\",\n" +
                    "    \"itemId\" : \"20000913\",\n" +
                    "    \"itemNo\" : \"γ-GT\",\n" +
                    "    \"itemName\" : \"γ-谷氨酰转肽酶\",\n" +
                    "    \"itemEname\" : \"GGT\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"U/L\",\n" +
                    "    \"itemRange\" : \"0~50\",\n" +
                    "    \"itemRangeHigh\" : \"50\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"35.00\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 7,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:01\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131034\",\n" +
                    "    \"itemId\" : \"20000798\",\n" +
                    "    \"itemNo\" : \"Urea\",\n" +
                    "    \"itemName\" : \"尿素\",\n" +
                    "    \"itemEname\" : \"Urea\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"3.1~8.0\",\n" +
                    "    \"itemRangeHigh\" : \"8.0\",\n" +
                    "    \"itemRangeLow\" : \"3.1\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"5.1\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 8,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:06\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131038\",\n" +
                    "    \"itemId\" : \"20000283\",\n" +
                    "    \"itemNo\" : \"Cr\",\n" +
                    "    \"itemName\" : \"肌酐\",\n" +
                    "    \"itemEname\" : \"Cr\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"umol/L\",\n" +
                    "    \"itemRange\" : \"57~97\",\n" +
                    "    \"itemRangeHigh\" : \"97\",\n" +
                    "    \"itemRangeLow\" : \"57\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"65.20\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 10,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:15\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131042\",\n" +
                    "    \"itemId\" : \"20000794\",\n" +
                    "    \"itemNo\" : \"UA\",\n" +
                    "    \"itemName\" : \"尿酸\",\n" +
                    "    \"itemEname\" : \"UA\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"umol/L\",\n" +
                    "    \"itemRange\" : \"90~428\",\n" +
                    "    \"itemRangeHigh\" : \"428\",\n" +
                    "    \"itemRangeLow\" : \"90\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"110.30\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 12,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:27\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131046\",\n" +
                    "    \"itemId\" : \"20000755\",\n" +
                    "    \"itemNo\" : \"TCH\",\n" +
                    "    \"itemName\" : \"总胆固醇\",\n" +
                    "    \"itemEname\" : \"CHOL\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"0~5.20\",\n" +
                    "    \"itemRangeHigh\" : \"5.20\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"3.40\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 13,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:36\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131050\",\n" +
                    "    \"itemId\" : \"20000761\",\n" +
                    "    \"itemNo\" : \"TG\",\n" +
                    "    \"itemName\" : \"甘油三脂\",\n" +
                    "    \"itemEname\" : \"TG\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"0.20~1.80\",\n" +
                    "    \"itemRangeHigh\" : \"1.80\",\n" +
                    "    \"itemRangeLow\" : \"0.20\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"1.06\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 14,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:40\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131054\",\n" +
                    "    \"itemId\" : \"20000397\",\n" +
                    "    \"itemNo\" : \"HDL-C\",\n" +
                    "    \"itemName\" : \"高密度脂蛋白\",\n" +
                    "    \"itemEname\" : \"HDL-C\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"0.80~2.04\",\n" +
                    "    \"itemRangeHigh\" : \"2.04\",\n" +
                    "    \"itemRangeLow\" : \"0.80\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"1.00\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 15,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:38:50\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131058\",\n" +
                    "    \"itemId\" : \"20000536\",\n" +
                    "    \"itemNo\" : \"LDL-C\",\n" +
                    "    \"itemName\" : \"低密度脂蛋白\",\n" +
                    "    \"itemEname\" : \"LDL-C\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"2.00~3.60\",\n" +
                    "    \"itemRangeHigh\" : \"3.60\",\n" +
                    "    \"itemRangeLow\" : \"2.00\",\n" +
                    "    \"labItemNo\" : \"1506431\",\n" +
                    "    \"itemResult\" : \"2.40\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 16,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:39:06\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006040853001\",\n" +
                    "    \"keynoGroup\" : \"20231006040853001\",\n" +
                    "    \"rptDetailId\" : \"6100753893689131062\",\n" +
                    "    \"itemId\" : \"20000171\",\n" +
                    "    \"itemNo\" : \"BS\",\n" +
                    "    \"itemName\" : \"血糖\",\n" +
                    "    \"itemEname\" : \"GLU\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"mmol/L\",\n" +
                    "    \"itemRange\" : \"3.9~6.1\",\n" +
                    "    \"itemRangeHigh\" : \"6.1\",\n" +
                    "    \"itemRangeLow\" : \"3.9\",\n" +
                    "    \"labItemNo\" : \"78016\",\n" +
                    "    \"itemResult\" : \"5.02\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 17,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 08:39:13\",\n" +
                    "    \"sampleClassId\" : \"20000207\",\n" +
                    "    \"sampleClassName\" : \"血清\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491456831490\",\n" +
                    "    \"itemId\" : \"20000807\",\n" +
                    "    \"itemNo\" : \"WBC\",\n" +
                    "    \"itemName\" : \"白细胞计数\",\n" +
                    "    \"itemEname\" : \"WBC\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"3.5~9.5\",\n" +
                    "    \"itemRangeHigh\" : \"9.5\",\n" +
                    "    \"itemRangeLow\" : \"3.5\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"4.62\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 1,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:06\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603649\",\n" +
                    "    \"itemId\" : \"20000687\",\n" +
                    "    \"itemNo\" : \"RBC\",\n" +
                    "    \"itemName\" : \"红细胞计数\",\n" +
                    "    \"itemEname\" : \"RBC\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^12/L\",\n" +
                    "    \"itemRange\" : \"4.3~5.8\",\n" +
                    "    \"itemRangeHigh\" : \"5.8\",\n" +
                    "    \"itemRangeLow\" : \"4.3\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"5.45\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 2,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:06\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603652\",\n" +
                    "    \"itemId\" : \"20000401\",\n" +
                    "    \"itemNo\" : \"HGB\",\n" +
                    "    \"itemName\" : \"血红蛋白浓度\",\n" +
                    "    \"itemEname\" : \"HGB\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"g/L\",\n" +
                    "    \"itemRange\" : \"130~175\",\n" +
                    "    \"itemRangeHigh\" : \"175\",\n" +
                    "    \"itemRangeLow\" : \"130\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"151.00\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 3,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603656\",\n" +
                    "    \"itemId\" : \"20000393\",\n" +
                    "    \"itemNo\" : \"HCT\",\n" +
                    "    \"itemName\" : \"红细胞压积\",\n" +
                    "    \"itemEname\" : \"HCT\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"40~50\",\n" +
                    "    \"itemRangeHigh\" : \"50\",\n" +
                    "    \"itemRangeLow\" : \"40\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"45.3\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 6,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603660\",\n" +
                    "    \"itemId\" : \"20000645\",\n" +
                    "    \"itemNo\" : \"PLT\",\n" +
                    "    \"itemName\" : \"血小板\",\n" +
                    "    \"itemEname\" : \"PLT\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"125~350\",\n" +
                    "    \"itemRangeHigh\" : \"350\",\n" +
                    "    \"itemRangeLow\" : \"125\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"176\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 7,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603664\",\n" +
                    "    \"itemId\" : \"20000557\",\n" +
                    "    \"itemNo\" : \"LYM%\",\n" +
                    "    \"itemName\" : \"淋巴细胞百分数\",\n" +
                    "    \"itemEname\" : \"LYM%\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"20~40\",\n" +
                    "    \"itemRangeHigh\" : \"40\",\n" +
                    "    \"itemRangeLow\" : \"20\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"39.20\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 8,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603668\",\n" +
                    "    \"itemId\" : \"20000595\",\n" +
                    "    \"itemNo\" : \"NEU%\",\n" +
                    "    \"itemName\" : \"中性粒细胞百分数\",\n" +
                    "    \"itemEname\" : \"NEU%\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"45~77\",\n" +
                    "    \"itemRangeHigh\" : \"77\",\n" +
                    "    \"itemRangeLow\" : \"45\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"49.2\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 9,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603672\",\n" +
                    "    \"itemId\" : \"20000579\",\n" +
                    "    \"itemNo\" : \"MON%\",\n" +
                    "    \"itemName\" : \"单核细胞百分数\",\n" +
                    "    \"itemEname\" : \"MON%\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"3~8\",\n" +
                    "    \"itemRangeHigh\" : \"8\",\n" +
                    "    \"itemRangeLow\" : \"3\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"6.50\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 10,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603676\",\n" +
                    "    \"itemId\" : \"20000320\",\n" +
                    "    \"itemNo\" : \"EOS%\",\n" +
                    "    \"itemName\" : \"嗜酸性粒细胞百分数\",\n" +
                    "    \"itemEname\" : \"EO%\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"0.5~5\",\n" +
                    "    \"itemRangeHigh\" : \"5\",\n" +
                    "    \"itemRangeLow\" : \"0.5\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"1.7\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 11,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603680\",\n" +
                    "    \"itemId\" : \"20000161\",\n" +
                    "    \"itemNo\" : \"BAS%\",\n" +
                    "    \"itemName\" : \"嗜碱性粒细胞百分数\",\n" +
                    "    \"itemEname\" : \"BAS%\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"0~1\",\n" +
                    "    \"itemRangeHigh\" : \"1\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.40\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 12,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:03\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603684\",\n" +
                    "    \"itemId\" : \"20000556\",\n" +
                    "    \"itemNo\" : \"LYM#\",\n" +
                    "    \"itemName\" : \"淋巴细胞计数\",\n" +
                    "    \"itemEname\" : \"LYM#\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"0.8~4\",\n" +
                    "    \"itemRangeHigh\" : \"4\",\n" +
                    "    \"itemRangeLow\" : \"0.8\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"1.95\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 13,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603688\",\n" +
                    "    \"itemId\" : \"20000594\",\n" +
                    "    \"itemNo\" : \"NEU#\",\n" +
                    "    \"itemName\" : \"中性粒细胞计数\",\n" +
                    "    \"itemEname\" : \"NEU#\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"2.0~7.7\",\n" +
                    "    \"itemRangeHigh\" : \"7.7\",\n" +
                    "    \"itemRangeLow\" : \"2.0\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"2.27\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 14,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603692\",\n" +
                    "    \"itemId\" : \"20000578\",\n" +
                    "    \"itemNo\" : \"MON#\",\n" +
                    "    \"itemName\" : \"单核细胞计数\",\n" +
                    "    \"itemEname\" : \"MON#\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"0.12~0.8\",\n" +
                    "    \"itemRangeHigh\" : \"0.8\",\n" +
                    "    \"itemRangeLow\" : \"0.12\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.30\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 15,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603695\",\n" +
                    "    \"itemId\" : \"20000319\",\n" +
                    "    \"itemNo\" : \"EOS#\",\n" +
                    "    \"itemName\" : \"嗜酸性粒细胞计数\",\n" +
                    "    \"itemEname\" : \"EO#\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"0.05~0.50\",\n" +
                    "    \"itemRangeHigh\" : \"0.50\",\n" +
                    "    \"itemRangeLow\" : \"0.05\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.08\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 16,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:03\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603697\",\n" +
                    "    \"itemId\" : \"20000160\",\n" +
                    "    \"itemNo\" : \"BAS#\",\n" +
                    "    \"itemName\" : \"嗜碱性粒细胞计数\",\n" +
                    "    \"itemEname\" : \"BAS#\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"0~0.1\",\n" +
                    "    \"itemRangeHigh\" : \"0.1\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.02\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 17,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:03\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603700\",\n" +
                    "    \"itemId\" : \"20000563\",\n" +
                    "    \"itemNo\" : \"MCV\",\n" +
                    "    \"itemName\" : \"红细胞平均体积\",\n" +
                    "    \"itemEname\" : \"MCV\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"fL\",\n" +
                    "    \"itemRange\" : \"82~100\",\n" +
                    "    \"itemRangeHigh\" : \"100\",\n" +
                    "    \"itemRangeLow\" : \"82\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"83.10\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 18,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603704\",\n" +
                    "    \"itemId\" : \"20000561\",\n" +
                    "    \"itemNo\" : \"MCH\",\n" +
                    "    \"itemName\" : \"平均血红蛋白含量\",\n" +
                    "    \"itemEname\" : \"MCH\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"pg\",\n" +
                    "    \"itemRange\" : \"27~34\",\n" +
                    "    \"itemRangeHigh\" : \"34\",\n" +
                    "    \"itemRangeLow\" : \"27\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"27.7\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 19,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603708\",\n" +
                    "    \"itemId\" : \"20000562\",\n" +
                    "    \"itemNo\" : \"MCHC\",\n" +
                    "    \"itemName\" : \"平均血红蛋白浓度\",\n" +
                    "    \"itemEname\" : \"MCHC\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"g/dL\",\n" +
                    "    \"itemRange\" : \"316~354\",\n" +
                    "    \"itemRangeHigh\" : \"354\",\n" +
                    "    \"itemRangeLow\" : \"316\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"333.00\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 20,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491624603713\",\n" +
                    "    \"itemId\" : \"20000691\",\n" +
                    "    \"itemNo\" : \"RDW-SD\",\n" +
                    "    \"itemName\" : \"红细胞分布宽度\",\n" +
                    "    \"itemEname\" : \"RDW-SD\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"fL\",\n" +
                    "    \"itemRange\" : \"37~50\",\n" +
                    "    \"itemRangeHigh\" : \"50\",\n" +
                    "    \"itemRangeLow\" : \"37\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"39.40\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 21,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:06\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158088\",\n" +
                    "    \"itemId\" : \"20000690\",\n" +
                    "    \"itemNo\" : \"RDW-CV\",\n" +
                    "    \"itemName\" : \"红细胞分布宽度-CV\",\n" +
                    "    \"itemEname\" : \"RDW-CV\",\n" +
                    "    \"itemRangeType\" : \"\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : null,\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"13.2\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 22,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:06\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158089\",\n" +
                    "    \"itemId\" : \"20000632\",\n" +
                    "    \"itemNo\" : \"PCT\",\n" +
                    "    \"itemName\" : \"血小板压积\",\n" +
                    "    \"itemEname\" : \"PCT\",\n" +
                    "    \"itemRangeType\" : \"\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : null,\n" +
                    "    \"itemRangeHigh\" : null,\n" +
                    "    \"itemRangeLow\" : null,\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.17\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 23,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158090\",\n" +
                    "    \"itemId\" : \"20000584\",\n" +
                    "    \"itemNo\" : \"MPV\",\n" +
                    "    \"itemName\" : \"血小板平均体积\",\n" +
                    "    \"itemEname\" : \"MPV\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"fL\",\n" +
                    "    \"itemRange\" : \"9~13\",\n" +
                    "    \"itemRangeHigh\" : \"13\",\n" +
                    "    \"itemRangeLow\" : \"9\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"9.90\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 24,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158094\",\n" +
                    "    \"itemId\" : \"20000635\",\n" +
                    "    \"itemNo\" : \"PDW\",\n" +
                    "    \"itemName\" : \"血小板体积分布宽度\",\n" +
                    "    \"itemEname\" : \"PDW\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"fL\",\n" +
                    "    \"itemRange\" : \"9~13\",\n" +
                    "    \"itemRangeHigh\" : \"13\",\n" +
                    "    \"itemRangeLow\" : \"9\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"11.10\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 25,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158098\",\n" +
                    "    \"itemId\" : \"20000644\",\n" +
                    "    \"itemNo\" : \"P-LCR\",\n" +
                    "    \"itemName\" : \"大型血小板比率\",\n" +
                    "    \"itemEname\" : \"P-LCR\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"13~43\",\n" +
                    "    \"itemRangeHigh\" : \"43\",\n" +
                    "    \"itemRangeLow\" : \"13\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"23.70\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 26,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:05\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158102\",\n" +
                    "    \"itemId\" : \"20000460\",\n" +
                    "    \"itemNo\" : \"IG#\",\n" +
                    "    \"itemName\" : \"幼稚粒细胞绝对值\",\n" +
                    "    \"itemEname\" : \"IG#\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"10^9/L\",\n" +
                    "    \"itemRange\" : \"0~0.06\",\n" +
                    "    \"itemRangeHigh\" : \"0.06\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.01\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 28,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  }, {\n" +
                    "    \"reportId\" : \"20231006095353001\",\n" +
                    "    \"keynoGroup\" : \"20231006095353001\",\n" +
                    "    \"rptDetailId\" : \"6100894491658158106\",\n" +
                    "    \"itemId\" : \"20000461\",\n" +
                    "    \"itemNo\" : \"IG%\",\n" +
                    "    \"itemName\" : \"幼稚粒细胞百分比\",\n" +
                    "    \"itemEname\" : \"IG%\",\n" +
                    "    \"itemRangeType\" : \"1\",\n" +
                    "    \"unit\" : \"%\",\n" +
                    "    \"itemRange\" : \"0~0.6\",\n" +
                    "    \"itemRangeHigh\" : \"0.6\",\n" +
                    "    \"itemRangeLow\" : \"0\",\n" +
                    "    \"labItemNo\" : \"77834\",\n" +
                    "    \"itemResult\" : \"0.20\",\n" +
                    "    \"mark\" : \"\",\n" +
                    "    \"sno\" : 29,\n" +
                    "    \"germNo\" : null,\n" +
                    "    \"germName\" : null,\n" +
                    "    \"yinYangNature\" : null,\n" +
                    "    \"expertOpinion\" : null,\n" +
                    "    \"reportNotes\" : null,\n" +
                    "    \"filingMemo\" : null,\n" +
                    "    \"smearMemo\" : null,\n" +
                    "    \"pageType\" : null,\n" +
                    "    \"antList\" : null,\n" +
                    "    \"flagCritical\" : 0,\n" +
                    "    \"collectTime\" : null,\n" +
                    "    \"resultTime\" : \"2023-10-06 09:52:04\",\n" +
                    "    \"sampleClassId\" : \"20000213\",\n" +
                    "    \"sampleClassName\" : \"血液\"\n" +
                    "  } ],\n" +
                    "  \"traceId\" : \"5c5f70fb-5117-44e9-8129-d4aca946ec22\",\n" +
                    "  \"exceptionName\" : null,\n" +
                    "  \"qualityMonitor\" : null,\n" +
                    "  \"ignoreQualityMonitor\" : false,\n" +
                    "  \"level\" : \"info\",\n" +
                    "  \"service\" : \"msun-middle-business-lis\",\n" +
                    "  \"businessException\" : false\n" +
                    "}";
            // 记录日志
            log.setReceiveMessage(res);
            // 判断
            if (!JSONUtil.isTypeJSON(res)) {
                throw new Exception("接口返回的结果不是json");
            }
            // 将返回信息转换为接收类
            ReportDetailResponse response = JSONUtil.toBean(res, ReportDetailResponse.class);
            // 判断
            if (BeanUtil.isEmpty(response) || CollUtil.isEmpty(response.getData()) || (!response.getSuccess())) {
                throw new RuntimeException("接口返回的数据为空或者接口返回错误！");
            }
            List<PeReportDepartmentDetail> peReportDepartmentDetails = new ArrayList<>();
            // 维护检验信息,循环，返回数据的信息进行维护赋值
            for (ReportDetailVo datum : response.getData()) {
                // 首先将每条信息存储在reportDetail表中
                ReportDetail reportDetail = new ReportDetail();
                List<ReportDetail> listhaveDone = reportDetailService.list(new LambdaQueryWrapper<ReportDetail>()
                        .eq(ReportDetail::getReportId, datum.getReportId())
                        .eq(ReportDetail::getRptDetailId, datum.getRptDetailId()));
                if (CollUtil.isNotEmpty(listhaveDone)) {
                    BeanUtil.copyProperties(datum, listhaveDone.get(0));
                    reportDetailService.saveOrUpdate(listhaveDone.get(0));
                } else {
                    BeanUtil.copyProperties(datum, reportDetail);
                    reportDetailService.saveOrUpdate(reportDetail);
                }
                // 维护生成图片需要的值
                // 是更新还是新增，暂时选择新增。
                // 首先根据获取这条报告的主项目编号和子项目编号
                List<CheckProject> collect = checkProjectList.stream().filter(Item -> Item.getLabItemId().equals(datum.getLabItemNo())).collect(Collectors.toList());
                List<CheckProjectDetail> collectDetail = checkProjectDetailList.stream().filter(item -> item.getItemId().equals(datum.getItemId())).collect(Collectors.toList());
                // 判断
                if (CollUtil.isEmpty(collect) || CollUtil.isEmpty(collectDetail)) {
                    log.log("项目：" + datum.getItemName() + "大项目号：" + datum.getLabItemNo() + "小项目号：" + datum.getItemId() + "|" + datum.getItemNo() + "," + "在项目维护表中没有找到对应的维护项目，无法存储相应信息！");
                    result = log.resultLog("项目：" + datum.getItemName() + "大项目号：" + datum.getLabItemNo() + "小项目号：" + datum.getItemId() + "|" + datum.getItemNo() + "," + "在项目维护表中没有找到对应的维护项目，无法存储相应信息！");
                }
                // 根据获取的项目名称对应生成维护数据，直接插入到pe_report_department_detail表中
                PeReportDepartmentDetail peReportDepartmentDetail = new PeReportDepartmentDetail();
                // 体检号
                peReportDepartmentDetail.setPatientNo(peRegister.getPatientNo());
                // 部门ID
                peReportDepartmentDetail.setDepartmentId(departmentUtil(collect.get(0).getMineItemRemork()));
                // 大项目号
                peReportDepartmentDetail.setComposeItemNo(collect.get(0).getMineItemRemork());
                // 小项目号
                peReportDepartmentDetail.setItemNo(collectDetail.get(0).getMineProjectNo());
                // 结果
                peReportDepartmentDetail.setPeResult(datum.getItemResult());
                // 上限值
                peReportDepartmentDetail.setNormalUp(upLow(datum.getItemRangeHigh()));
                // 下限值
                peReportDepartmentDetail.setNormalDown(upLow(datum.getItemRangeLow()));
                // 范围值
                peReportDepartmentDetail.setNormalPrint(datum.getItemRange());
                // 测试同步成功与否
                peReportDepartmentDetail.setReportDate(DateUtil.date());
                peReportDepartmentDetails.add(peReportDepartmentDetail);
            }
            // 数据持久化
            saveOrUpdateDataList(peReportDepartmentDetails);
//            peReportDepartmentDetailService.saveOrUpdateBatch(peReportDepartmentDetails);
            // 同步状态 1:完成 0 ：未完成
            peRegister.setIsReport(StaticValue.ONE.getCode());
            this.saveOrUpdate(peRegister);
            log.success(true);
            log.log("数据同步成功！");
            result = log.resultLog("数据同步成功！");
        } catch (Exception e) {
            // 接口请求失败
            log.success(false);
            // 日志记录
            log.log("报错：" + e.getMessage());
            result = log.resultLog("报错：" + e.getMessage());
            throw e;
        } finally {
            // 保存日志
            log.saveLog();
        }
        return result;
    }

    private void saveOrUpdateDataList(List<PeReportDepartmentDetail> peReportDepartmentDetails) throws Exception {
        // 维护人员状态，报告查询维护：已完成,手动sql
        for (PeReportDepartmentDetail peReportDepartmentDetail : peReportDepartmentDetails) {
            List<PeReportDepartmentDetail> isSave = peReportDepartmentDetailService.list(new LambdaQueryWrapper<PeReportDepartmentDetail>()
                    .eq(PeReportDepartmentDetail::getPatientNo, peReportDepartmentDetail.getPatientNo())
                    .eq(PeReportDepartmentDetail::getDepartmentId, peReportDepartmentDetail.getDepartmentId())
                    .eq(PeReportDepartmentDetail::getComposeItemNo, peReportDepartmentDetail.getComposeItemNo())
                    .eq(PeReportDepartmentDetail::getItemNo, peReportDepartmentDetail.getItemNo())
            );
            if (CollUtil.isEmpty(isSave)) {
                // 新增
                if (BeanUtil.isEmpty(peReportDepartmentDetail.getNormalDown()) && BeanUtil.isNotEmpty(peReportDepartmentDetail.getNormalUp())) {
                    peReportDepartmentDetailMapper.addUp(peReportDepartmentDetail);
                } else if (BeanUtil.isNotEmpty(peReportDepartmentDetail.getNormalDown()) && BeanUtil.isEmpty(peReportDepartmentDetail.getNormalUp())) {
                    peReportDepartmentDetailMapper.addDown(peReportDepartmentDetail);
                } else if (BeanUtil.isEmpty(peReportDepartmentDetail.getNormalDown()) && BeanUtil.isEmpty(peReportDepartmentDetail.getNormalUp())) {
                    peReportDepartmentDetailMapper.addNoUpDown(peReportDepartmentDetail);
                } else {
                    peReportDepartmentDetailMapper.add(peReportDepartmentDetail);
                }
            } else {
                // 修改
                if (BeanUtil.isEmpty(peReportDepartmentDetail.getNormalDown()) && BeanUtil.isNotEmpty(peReportDepartmentDetail.getNormalUp())) {
                    peReportDepartmentDetailMapper.editUp(peReportDepartmentDetail);
                } else if (BeanUtil.isNotEmpty(peReportDepartmentDetail.getNormalDown()) && BeanUtil.isEmpty(peReportDepartmentDetail.getNormalUp())) {
                    peReportDepartmentDetailMapper.editDown(peReportDepartmentDetail);
                } else if (BeanUtil.isEmpty(peReportDepartmentDetail.getNormalDown()) && BeanUtil.isEmpty(peReportDepartmentDetail.getNormalUp())) {
                    peReportDepartmentDetailMapper.editNoUpDown(peReportDepartmentDetail);
                } else {
                    peReportDepartmentDetailMapper.edit(peReportDepartmentDetail);
                }
            }
        }
    }

    public Integer departmentUtil(String composeItemNo) {
        switch (composeItemNo) {
            case "LNCT1003":
                return 13;
            case "LNCT1004":
                return 31;
            case "LNCT1005":
                return 14;
            case "LNCT1025":
                return 14;
            default:
                return 0;
        }
    }

    public Double upLow(String value) {
        if (StrUtil.isEmpty(value) || value.equals("null")) {
            // 如果上下限，为空，则赋值0
            return null;
        } else {
            try {
                // 如果上下限不为空，那么就赋值字符串转换的double值
                return Double.parseDouble(value);
            } catch (Exception e) {
                // 如果转换错误那么就返回空
                return null;
            }
        }
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
            log.log("这是最终返回的日志" + resultMessage);
            log.saveLog();
        }
        return Result.ok("测试结束");
    }
}
