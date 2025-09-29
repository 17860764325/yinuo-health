package org.jeecg.modules.doctor.entity;
import lombok.Data;
/**
 * @author lihaoran
 * @date 2025/9/19 23:46
 */


/**
 * 查询PACS报告及申请单图像接口 - 请求参数实体类
 */
@Data
public class PacsReportRequest {

    /**
     * 开始时间，格式：yyyy-MM-dd HH:mm:ss（非必选）
     */
    private String startDate;

    /**
     * 结束时间，格式：yyyy-MM-dd HH:mm:ss（非必选）
     */
    private String endDate;

    /**
     * 患者ID（非必选）
     */
    private String patId;

    /**
     * 住院号（非必选）
     */
    private String patInHosCode;

    /**
     * 患者类型数组{1:门诊, 2:住院, 3:体检}（非必选）
     */
    private Integer[] patType;

    /**
     * 医嘱业务ID数组（如有多个以逗号分隔，非必选）
     */
    private String[] personCombIds;

    /**
     * Acc号（非必选）
     */
    private String accessNum;

    /**
     * 检查流水号（非必选）
     */
    private String patientId;

    /**
     * 登记ID（非必选）
     * 优先级：registrationId > patientId > accessNum
     */
    private Integer registrationId;
}
