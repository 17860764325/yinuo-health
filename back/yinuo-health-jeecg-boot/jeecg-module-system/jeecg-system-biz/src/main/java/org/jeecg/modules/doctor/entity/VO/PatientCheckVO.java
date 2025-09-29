package org.jeecg.modules.doctor.entity.VO;

/**
 * @author lihaoran
 * @date 2025/9/19 22:25
 */

import lombok.Data;

import java.util.List;

/**
 * 患者检查信息详情VO
 */
@Data
public class PatientCheckVO {
    /**
     * 登记标识
     */
    private String regId;

    /**
     * 报告审核人姓名
     */
    private String auditDocName;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 申请科室名称
     */
    private String billDeptName;

    /**
     * 申请医生姓名
     */
    private String billDocName;

    /**
     * 生日（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String birthDate;

    /**
     * 检查结论
     */
    private String conclusion;

    /**
     * 是否急诊（0:否, 1:是）
     */
    private String emergencyFlag;

    /**
     * 检查所见
     */
    private String finding;

    /**
     * 随访标识（-1:未选择, 0:否, 1:是）
     */
    private Integer followStatus;

    /**
     * 姓名全码
     */
    private String fullCode;

    /**
     * 身份证号
     */
    private String idcardNo;

    /**
     * 是否已申请远程影像（0:否,1:是）
     */
    private String imageFlag;

    /**
     * 图像数量
     */
    private Integer imgCount;

    /**
     * 民族
     */
    private String nation;

    /**
     * 年龄
     */
    private Integer patAge;

    /**
     * 年龄单位（如岁、月）
     */
    private String patAgeunit;

    /**
     * 就诊卡号
     */
    private String patCardNo;

    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 患者类型（1:门诊, 2:住院, 3:体检）
     */
    private Integer patTypeId;

    /**
     * 登记时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String regDate;

    /**
     * 登记类型（1:普通登记, 2:绿色通道, 3:合作单位）
     */
    private Integer regTypeId;

    /**
     * 报到时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String registerTime;

    /**
     * 登记员姓名
     */
    private String registrarName;

    /**
     * 登记ID
     */
    private Long registrationId;

    /**
     * 报告审核时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String reportAuditTime;

    /**
     * 报告提交人姓名
     */
    private String reportDocName;

    /**
     * 报告PDF路径（访问地址）
     */
    private String reportPdfPath;

    /**
     * 报告提交时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String reportSubmitTime;

    /**
     * 病室号
     */
    private String roomNo;

    /**
     * 诊室流水号（当天）
     */
    private String roomSerialNum;

    /**
     * 危急报告标识（0:否, 1:是）
     */
    private String seriousFlag;

    /**
     * 性别（0:未知, 1:男, 2:女, 3:未定）
     */
    private Integer sexId;

    /**
     * 检查部位
     */
    private String stuBodypart;

    /**
     * 检查科室名称
     */
    private String stuDeptName;

    /**
     * 检查医生姓名
     */
    private String stuDocName;

    /**
     * 检查设备名称
     */
    private String stuEquipmentName;

    /**
     * 检查UID（最新一次，全部需调用2.8.16接口）
     */
    private String stuInstanceUid;

    /**
     * 检查方法名称
     */
    private String stuMethodName;

    /**
     * 检查模态（如CT、DR）
     */
    private String stuModalityVal;

    /**
     * 检查诊室名称
     */
    private String stuRoomName;

    /**
     * 检查流水号（总）
     */
    private String stuSerialNum;

    /**
     * 检查时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String stuTime;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 报告书写人姓名
     */
    private String writerName;

    /**
     * 流程状态（-1:未计费, 0:预约, 1:已登记...9:已过号）
     */
    private Integer processStatus;

    /**
     * 住院号
     */
    private String patInHosCode;

    /**
     * 医院ID
     */
    private Long hospitalId;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 胶片是否打印（0:未打印, 1:已打印）
     */
    private Integer filmPrintFlag;

    /**
     * 报告是否打印（0:未打印, 1:已打印）
     */
    private Integer reportPrintFlag;

    /**
     * 阳性率（-1:未选择, 0:阴性, 1:阳性, 2:待定,3:重大阳性）
     */
    private Integer positiveRateStatus;

    /**
     * 患者标识（检查号/影像号）
     */
    private String patientId;

    /**
     * 图像标识
     */
    private String accessionNumber;

    /**
     * 住院ID
     */
    private Long patInHosId;

    /**
     * 患者ID（关联患者信息唯一标识）
     */
    private Long patId;

    /**
     * 部位医嘱信息列表（needBodyInfo=true时返回）
     */
    private List<BodyInfoVO> bodyInfos;

    /**
     * 报告ID
     */
    private Long reportId;

    /**
     * 云胶片类型（0:实体胶片,1:云胶片,2:全部）
     */
    private Integer sendCloudFilm;

    /**
     * 内镜唯一编号（含RFID卡号）
     */
    private String endoscopeDeviceNo;

    /**
     * 检查金额
     */
    private Double amount;
}