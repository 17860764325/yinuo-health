package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;

import java.util.List;

/**
 * @author lihaoran
 * @date 2025/9/19 23:48
 */

/**
 * 患者检查信息详情实体类
 */
@Data
public class ThirdRegVO {

    /**
     * acc号
     */
    private String accessionNumber;

    /**
     * 患者地址
     */
    private String addressName;

    /**
     * 申请单ID集合
     */
    private List<String> applyFormDetailIds;

    /**
     * 审核医生编号
     */
    private Integer auditDocId;

    /**
     * 审核医生姓名
     */
    private String auditDocName;

    /**
     * 病案号
     */
    private String bedNo;

    /**
     * 申请科室ID
     */
    private Integer billDeptId;

    /**
     * 申请科室名称
     */
    private String billDeptName;

    /**
     * 申请医生ID
     */
    private Integer billDocId;

    /**
     * 申请医生姓名
     */
    private String billDocName;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 检查结论
     */
    private String conclusion;

    /**
     * 检查所见
     */
    private String finding;

    /**
     * 医院编号
     */
    private Integer hospitalId;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 身份证号
     */
    private String idcardNo;

    /**
     * 图像路径
     */
    private String imagePath;

    /**
     * 临床诊断
     */
    private String labApplyDiagnose;

    /**
     * 模态
     */
    private String modality;

    /**
     * 申请单ID
     */
    private Integer pacsApplyMainId;

    /**
     * 患者年龄
     */
    private String patAge;

    /**
     * 年龄单位
     */
    private String patAgeUnit;

    /**
     * 门诊就诊卡ID
     */
    private Integer patCardId;

    /**
     * 门诊就诊卡号
     */
    private String patCardNo;

    /**
     * 患者唯一标识
     */
    private Long patId;

    /**
     * 住院号
     */
    private String patInHosCode;

    /**
     * 住院号ID
     */
    private Long patInHosId;

    /**
     * 患者病史（病情摘要）
     */
    private String patMedicalHistory;

    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 患者类型{1:门诊, 2:住院, 3:体检}
     */
    private Integer patTypeId;

    /**
     * 检查号
     */
    private String patientId;

    /**
     * pdf路径
     */
    private String pdfUrl;

    /**
     * 阴阳性标识 -1 未选 0阴性 1阳性 2待定
     */
    private String positiveRateStatus;

    /**
     * 报告打印次数
     */
    private Integer printCount;

    /**
     * 胶片是否打印{0:未打印,1:已打印}
     */
    private String printFilmFlag;

    /**
     * 打印时间
     */
    private String printTime;

    /**
     * 流程状态{-1:未计费, 0:预约, 1:已登记, 2:已叫号, 3:已检查, 4:已提交, 5:已审核, 6:已撤销, 7:已取消, 8:已驳回, 9:已过号}
     */
    private Integer processStatus;

    /**
     * 诊室排队号
     */
    private Integer queueNumber;

    /**
     * 登记时间
     */
    private String regDate;

    /**
     * 患者登记类型{1:普通登记, 2:绿色通道, 3:合作单位}
     */
    private Integer regTypeId;

    /**
     * 报到时间
     */
    private String registerTime;

    /**
     * 登记id
     */
    private Long registrationId;

    /**
     * 报告审核时间
     */
    private String reportAuditTime;

    /**
     * 报告医生编号
     */
    private Integer reportDocId;

    /**
     * 报告医生姓名
     */
    private String reportDocName;

    /**
     * 报告id
     */
    private Long reportId;

    /**
     * 报告提交时间
     */
    private String reportSubmitTime;

    /**
     * 病案号
     */
    private String roomNo;

    /**
     * 患者性别
     */
    private String sexName;

    /**
     * 性别编号
     */
    private Integer sexId;

    /**
     * 检查部位
     */
    private String stuBodypart;

    /**
     * 检查设备ID
     */
    private Integer stuEquipmentId;

    /**
     * 检查设备名称
     */
    private String stuEquipmentName;

    /**
     * 检查方法
     */
    private String stuMethodName;

    /**
     * 检查流水号(总)
     */
    private String stuSerialNum;

    /**
     * 检查时间
     */
    private String stuTime;

    /**
     * 检查方法ID
     */
    private Integer studyMethodId;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 病区id
     */
    private Integer wardId;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 报告书写人
     */
    private String writerName;

    /**
     * 登记对应费用信息
     */
    private List<RegDetail> regDetails;

    /**
     * 申请单明细id集合
     */
    private List<String> pacsApplyDetailIds;

    /**
     * 部位信息
     */
    private List<BodyInfo> bodyInfos;

    /**
     * 补录项目明细
     */
    private List<ExpenseSupplementVO> expenseSupplementsVOS;
}
