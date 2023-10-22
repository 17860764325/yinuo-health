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
            String res = RequestUtil.go(INTERFACE_INFO.getUrl(), INTERFACE_INFO.getRequestType(), paramsMap);
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
                String res = RequestUtil.go(INTERFACE_INFO_PERSON_CREATE.getUrl(), INTERFACE_INFO_PERSON_CREATE.getRequestType(), paramMap);
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
                String res = RequestUtil.go(LIS_APPLY.getUrl(), LIS_APPLY.getRequestType(), paramMap);
            // 假数据
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
                String res = RequestUtil.go(BAR_CODE_BUILD.getUrl(), BAR_CODE_BUILD.getRequestType(), paramMap);
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
//            // 检验好，体检号
//            paramsMap.put(PAT_NO, peRegister.getPatientNo());
//            // 条码号
//            List<String> barcodeList = new ArrayList<>();
//            for (LisApplyBarCodeReportId lisApplyBarCodeReportId : list) {
//                if (StrUtil.isNotEmpty(lisApplyBarCodeReportId.getBarCode()) && CollUtil.isEmpty(barcodeList.stream().filter(item -> item.equals(lisApplyBarCodeReportId.getBarCode())).collect(Collectors.toList()))) {
//                    barcodeList.add(lisApplyBarCodeReportId.getBarCode());
//                }
//            }
//            // 判断筛选后的条码信息是否为空
//            if (CollUtil.isEmpty(barcodeList)) {
//                throw new RuntimeException("条码参数为空，条码关系表，并没有维护条码信息");
//            }
//            paramsMap.put(BAR_CODE_LIST, barcodeList);
            // 发送参数
            log.setSendMessage(JSONUtil.parse(paramsMap).toString());
            // 发送请求
            String res = RequestUtil.go(REPORT_ID_SEARCH.getUrl(), REPORT_ID_SEARCH.getRequestType(), paramsMap);
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
            String res = RequestUtil.go(REPORT_DETAIL_SEARCH.getUrl(), REPORT_DETAIL_SEARCH.getRequestType(), paramsMap);
            // 假数据
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
