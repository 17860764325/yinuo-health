package org.jeecg.modules.doctor.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.DhyPrescriptionRequest;
import org.jeecg.modules.doctor.entity.DhyPrescriptionResponse;
import org.jeecg.modules.doctor.entity.PeRegisterList;

/**
 * @Description: 东华原煎药系统服务接口
 * @Author: lihaoran
 * @Date: 2026-01-10
 * @Version: V1.0
 */
public interface IDhyPrescriptionService {

    /**
     * 处方传入接口
     * 医院系统向东华原系统下发处方
     *
     * @param request 处方请求参数
     * @param peRegister 患者登记信息（用于日志记录）
     * @return 处方传入结果
     */
    Result<DhyPrescriptionResponse> dhyPrescriptionService(DhyPrescriptionRequest request, PeRegisterList peRegister);

    /**
     * 处方传入接口（通过JSON字符串）
     * 医院系统向东华原系统下发处方
     *
     * @param jsondhy JSON格式的处方数据
     * @param peRegister 患者登记信息（用于日志记录）
     * @return 处方传入结果
     */
    Result<DhyPrescriptionResponse> dhyPrescriptionService(String jsondhy, PeRegisterList peRegister);
}