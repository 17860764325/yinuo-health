package org.jeecg.modules.doctor.entity;
import lombok.Data;
import org.jeecg.modules.doctor.entity.VO.QualityMonitor;
import org.jeecg.modules.doctor.entity.VO.ThirdRegVO;

import java.util.List;
/**
 * @author lihaoran
 * @date 2025/9/19 23:46
 */


/**
 * 查询PACS报告及申请单图像接口 - 响应结果实体类
 */
@Data
public class PacsReportResponse {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 是否将返回值包装为ResponseResult
     */
    private Boolean decorate;

    /**
     * 错误码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 业务数据列表
     */
    private List<ThirdRegVO> data;

    /**
     * 跟踪ID
     */
    private String traceId;

    /**
     * 异常名称
     */
    private String exceptionName;

    /**
     * 是否是业务异常
     */
    private Boolean businessException;

    /**
     * 忽略质控结果
     */
    private Boolean ignoreQualityMonitor;

    /**
     * 质控结果
     */
    private QualityMonitor qualityMonitor;
}
