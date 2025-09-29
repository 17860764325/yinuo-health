package org.jeecg.modules.doctor.entity;
import lombok.Data;
import org.jeecg.modules.doctor.entity.VO.PatientCheckVO;

import java.util.List;
/**
 * @author lihaoran
 * @date 2025/9/19 22:24
 */


/**
 * 获取患者检查信息接口 - 根返回结果
 */
@Data
public class PatCheckResponse {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 是否包装为ResponseResult
     */
    private Boolean decorate;

    /**
     * 错误码（0000表示成功）
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 患者检查信息列表
     */
    private List<PatientCheckVO> data;

    /**
     * 跟踪ID
     */
    private String traceId;

    /**
     * 异常名称
     */
    private String exceptionName;

    /**
     * 是否业务异常
     */
    private Boolean businessException;

    /**
     * 是否忽略质控结果
     */
    private Boolean ignoreQualityMonitor;
}