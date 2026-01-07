package org.jeecg.modules.doctor.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lihaoran
 * @date 2025/9/19 22:24
 */

/**
 * 获取患者检查信息接口 - 请求参数实体类
 */
@Data
public class PatCheckQueryRequest {


    /**
     * 开始时间（报到时间/登记时间），格式：yyyy-MM-dd HH:mm:ss
     */
    @NotBlank(message = "开始时间startDate不能为空")
    private String startDate;

    /**
     * 结束时间（报到时间/登记时间），格式：yyyy-MM-dd HH:mm:ss
     */
    @NotBlank(message = "结束时间endDate不能为空")
    private String endDate;

    /**
     * Acc号（非必选）
     */
    private Integer accessNum;

    /**
     * 检查流水号（非必选）
     */
    private String patientId;

    /**
     * 流程状态（非必选）
     * 1:已登记, 2:已叫号, 3:已检查, 4:已提交, 5:已审核, 6:已撤销, 7:已取消, 8:已驳回, 9:已过号
     */
    private Integer processStatus;

    /**
     * 是否按审核时间查询（非必选，不为空则按审核时间查）
     */
    private String auditTimeFlag;

    /**
     * 检查模态（非必选，如CT、DR）
     */
    private String modality;

    /**
     * 患者ID（关联患者信息唯一标识，非必选）
     */
    private String patId;

    /**
     * 患者住院ID（非必选）
     */
    private Integer patInHosId;

    /**
     * 患者姓名（精准查询，非必选）
     */
    private String patName;

    /**
     * 是否需要部位医嘱信息（非必选，true需要，默认false）
     */
    private Boolean needBodyInfo = false;

    /**
     * 身份证号（非必选）
     */
    private String idcardNo;

    /**
     * 危急报告标识（非必选）0:否, 1:是
     */
    private String seriousFlag;

    /**
     * 是否过滤自助机不可打印报告（必选）
     * 0:不过滤, 1:过滤（胶片自助机传1，影像云传0）
     */
    @NotNull(message = "是否过滤不可打印报告filterPrintFlag不能为空")
    private Integer filterPrintFlag;

    /**
     * 是否按检查时间查询（非必选，1:按检查时间查）
     */
    private Integer useStuTimeFlag;
}