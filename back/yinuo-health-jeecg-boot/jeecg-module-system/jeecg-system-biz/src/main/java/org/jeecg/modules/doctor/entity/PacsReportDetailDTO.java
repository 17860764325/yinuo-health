package org.jeecg.modules.doctor.entity;

import lombok.Data;

/**
 * PACS报告详情实体类（接口返回data数组中的数据结构）
 * @author 开发者
 * @date 2026-01-25
 */
@Data
public class PacsReportDetailDTO {
    /** 报告医生名称 */
    private String reportDocName;

    /** 审核医生名称 */
    private String auditDocName;

    /** 报告提交时间 */
    private String reportSubmitTime;

    /** 报告审核时间 */
    private String reportAuditTime;

    /** 检查所见 */
    private String finding;

    /** 检查结论 */
    private String conclusion;

    /** 危急值标识（null表示无） */
    private Object seriousFlag;

    /** 阳性率状态（null表示无） */
    private Object positiveRateStatus;

    /** 报告PDF文件路径（可直接下载） */
    private String reportPdfPath;

    /** 报告内容（null表示无） */
    private Object reportContent;

    /** JSON格式报告内容 */
    private String jsonReportContent;

    /** 检查模态（如CT、CR等） */
    private String modality;

    /** 完成时间 */
    private String stuTime;

    /** 提交医生签名图片（null表示无） */
    private Object submitDocSignImg;

    /** 审核医生签名图片（null表示无） */
    private Object auditDocSignImg;

    /** 流程状态（5表示报告已审核完成） */
    private Integer processStatus;
}