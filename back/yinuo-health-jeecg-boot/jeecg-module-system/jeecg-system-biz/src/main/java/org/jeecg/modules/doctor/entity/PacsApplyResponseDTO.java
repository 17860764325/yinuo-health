package org.jeecg.modules.doctor.entity;

import lombok.Data;
import java.util.List;

/**
 * 第三方PACS接口响应实体类
 * @author 开发者
 * @date 2026-01-25
 */
@Data
public class PacsApplyResponseDTO {
    /** 接口调用是否成功 */
    private Boolean success;

    /** 是否装饰返回结果 */
    private Boolean decorate;

    /** 响应码（0000表示成功） */
    private String code;

    /** 响应消息 */
    private String message;

    /** 响应数据（申请单ID列表） */
    private List<String> data;

    /** 追踪ID，用于问题排查 */
    private String traceId;

    /** 异常名称 */
    private String exceptionName;

    /** 质量监控信息 */
    private Object qualityMonitor;

    /** 是否忽略质量监控 */
    private Boolean ignoreQualityMonitor;

    /** 日志级别 */
    private String level;

    /** 服务名称 */
    private String service;

    /** 是否为业务异常 */
    private Boolean businessException;
}