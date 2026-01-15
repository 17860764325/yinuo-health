package org.jeecg.modules.doctor.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Description: 东华原处方传入接口请求实体类
 * @Author: lihaoran
 * @Date: 2026-01-10
 */
@Data
public class DhyPrescriptionRequest {

    /**
     * 处方信息列表（必填）
     */
    @NotEmpty(message = "处方信息不能为空")
    @Valid
    private List<DhyPrescription> prescription;

    /**
     * 药品信息列表（必填）
     */
    @NotEmpty(message = "药品信息不能为空")
    @Valid
    private List<DhyDrug> drug;
}