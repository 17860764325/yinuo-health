package org.jeecg.modules.doctor.util;

import org.jeecg.modules.doctor.entity.InterfaceRequestLog;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import org.jeecg.modules.doctor.service.IInterfaceRequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lihaoran
 * @date 10/11/23 6:32 PM
 * 日志请求记录工具类
 */
@Component
public class LogUtil {

    @Autowired
    private IInterfaceRequestLogService interfaceRequestLogService;



    /**
     * @description: log 和 类返回信息的组装
     * @param: resultMessage 返回信息
     * peRegister 人员信息
     * logMessage 日志信息
     * message  具体信息
     * @return: void
     * @author lhr
     * @date: 10/13/23 12:41 AM
     */
    public void logMessage(StringBuffer resultMessage, PeRegisterList peRegister, StringBuffer logMessage, String message) {
        logMessage.append("患者：" + peRegister.getPatientName() + "," + message);
        resultMessage.append("患者：" + peRegister.getPatientName() + "," + message);
    }

    /**
     * @description: 日志基础信息封装
     * @param: peRegister 人员信息
     * interfaceInfo 接枚举
     * log 日志实体类
     * @return: void
     * @author lhr
     * @date: 10/13/23 12:46 AM
     */
    public void logBasicInfo(PeRegisterList peRegister, InterfaceInfo interfaceInfo, InterfaceRequestLog log) {
        // 日志针对人员的体检号
        log.setPatientNo(peRegister.getPatientNo());
        // 姓名
        log.setPatientName(peRegister.getPatientName());
        // 患者 id 记录
        log.setPatId(peRegister.getPatId());
        // 接口编码
        log.setInterfaceCode(interfaceInfo.getCode());
        // 接口名称
        log.setInterfaceName(interfaceInfo.getRemark());
    }

    /**
     * @description: 日志保存
     * @param: log 日志实体类
     * @return: void
     * @author lhr
     * @date: 10/13/23 12:48 AM
     */
    public void saveLog(InterfaceRequestLog log) {
        interfaceRequestLogService.save(log);
    }


}
