package org.jeecg.modules.doctor.util;

import org.jeecg.modules.doctor.entity.InterfaceRequestLog;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import org.jeecg.modules.doctor.service.impl.InterfaceRequestLogServiceImpl;

/**
 * @author lihaoran
 * @date 10/13/23 2:08 AM
 */
public class LogUtilNew {

    private InterfaceRequestLog log;
    /**
     * log
     */
    private StringBuffer logMessage;

    // 构造函数，构建基础的信息
    LogUtilNew(InterfaceInfo interfaceInfo, PeRegisterList peRegister) {
        this.log = new InterfaceRequestLog();
        // 日志针对人员的体检号
        this.log.setPatientNo(peRegister.getPatientNo());
        // 姓名
        this.log.setPatientName(peRegister.getPatientName());
        // 患者 id 记录
        this.log.setPatId(peRegister.getPatId());
        // 接口编码
        this.log.setInterfaceCode(interfaceInfo.getCode());
        // 接口名称
        this.log.setInterfaceName(interfaceInfo.getRemark());
        // log
        this.logMessage = new StringBuffer();
    }

    // 获取构造函数
    public static LogUtilNew getInstance(InterfaceInfo interfaceInfo, PeRegisterList peRegister) {
        return new LogUtilNew(interfaceInfo, peRegister);
    }


    // 错误日志
    public StringBuffer errorLog(String message) {
        this.log.setSuccess(StaticValue.FAIL.getCode());
        this.logMessage.append(message);
        return this.logMessage;
    }

    // 日志
    public StringBuffer log(String message) {
        this.logMessage.append(message);
        return this.logMessage;
    }

    // 是否成功标识
    public void success(Boolean flag) {
        this.log.setSuccess(flag ? StaticValue.SUCCESS.getCode() : StaticValue.FAIL.getCode());
    }

    // 发送请求信息
    public void setSendMessage(String  message) {
      this.log.setSendMessage(message);
    }

    // 接受请求信息
    public void setReceiveMessage(String  message) {
        this.log.setReceiveMessage(message);
    }

    // 保存
    public InterfaceRequestLog saveLog() {
        InterfaceRequestLogServiceImpl bean = SpringUtils.getBean(InterfaceRequestLogServiceImpl.class);
        this.log.setRemark(this.logMessage.toString());
        bean.save(this.log);
        return this.log;
    }
}
