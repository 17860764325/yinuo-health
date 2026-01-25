package org.jeecg.modules.doctor.entity;

import lombok.Data;
import java.util.List;

/**
 * 第三方PACS接口申请单主入参实体类
 * @author 开发者
 * @date 2026-01-25
 */
@Data
public class PacsApplyRequestDTO {
    /** 自己系统申请单号，默认值：987654321 */
    private String physicalNo ;

    /** 身份证号（患者信息），默认值：3758975414521 */
    private String idcardNo ;

    /** 患者电话（患者信息），默认值：15550485456 */
    private String applyTelephone ;

    /** 申请科室ID，默认值：7035099002056605704 */
    private Long billDeptId = 7035099002056605704L;

    /** 申请科室名称，默认值：广文社区 */
    private String billDeptName = "广文社区";

    /** 申请医生Id，默认值：8489752841619637124 */
    private Long billDocId ;

    /** 申请医生名称，默认值：社区体检 */
    private String billDocName ;

    /** 临床诊断名称，默认值：XXXX */
    private String labApplyDiagnose ;

    /** 患者年龄（患者信息），默认值：25 */
    private String patAge ;

    /** 年龄单位（患者信息），默认值：岁 */
    private String patAgeUnit ="岁";

    /** 就诊卡号（众阳系统患者卡号），默认值：SN80000000017293 */
    private String patCardNo ;

    /** 患者id（众阳系统），默认值：4742398333063139000 */
    private Long patId;

    /** 既往史（自己系统传），默认值：xxxxxx */
    private String patMedicalHistory ;

    /** 患者姓名（患者信息），默认值：张寿 */
    private String patName ;

    /** 患者类型{1:门诊, 2:住院, 3:体检}，默认值：3 */
    private Integer patType = 3;

    /** 申请时间（当前时间），默认值：2022-06-28 09:58:36 */
    private String reqTime ;

    /** 性别编号{2:女,1:男}，默认值：1 */
    private Integer sexId ;

    /** 患者性别，默认值：男 */
    private String sexName ;

    /** 申请单来源{0:pacs直接下申请, 1:临床路径生成, 2:电子病历自动生成申请单, 3:医信调用生成, 4:一体化申请, 5:手术室}，默认值：0 */
    private Integer source = 0;

    /** 出生日期（患者的出生日期），默认值：2020-12-12 00:00:00 */
    private String birthDay ;

    /** 申请单明细列表 */
    private List<ThirdApplyDetailDTO> thirdApplyDetailDtoList;
}