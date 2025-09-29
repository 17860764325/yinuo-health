package org.jeecg.modules.doctor.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lihaoran
 * @date 2025/9/19 23:42
 */

/**
 * 获取检查项目接口 - 请求参数实体类
 */
@Data
public class CheckItemRequest {

    /**
     * 医院ID（必选）
     */
    @NotNull(message = "医院ID(hospitalId)不能为空")
    private Integer hospitalId;

    /**
     * 执行科室ID（必选）
     */
    @NotNull(message = "执行科室ID(exeDeptId)不能为空")
    private Integer exeDeptId;
}
