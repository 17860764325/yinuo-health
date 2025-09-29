package org.jeecg.modules.doctor.entity;
import lombok.Data;
import org.jeecg.modules.doctor.entity.VO.CheckItemData;

import java.util.List;
/**
 * @author lihaoran
 * @date 2025/9/19 23:42
 */

/**
 * 获取检查项目接口 - 响应结果实体类（根节点）
 */
@Data
public class CheckItemResponse {

    /**
     * 标记是否成功
     */
    private Boolean success;

    /**
     * 是否将返回值包装为ResponseResult
     */
    private Boolean decorate;

    /**
     * 错误码（0000表示成功）
     */
    private String code;

    /**
     * 跟踪ID
     */
    private String traceId;

    /**
     * 是否是业务异常
     */
    private Boolean businessException;

    /**
     * 异常名称
     */
    private String exceptionName;

    /**
     * 业务数据对象列表
     */
    private List<CheckItemData> data;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 是否忽略质控结果
     */
    private Boolean ignoreQualityMonitor;

    /**
     * 质控信息
     */
    private Object qualityMonitor;
}
