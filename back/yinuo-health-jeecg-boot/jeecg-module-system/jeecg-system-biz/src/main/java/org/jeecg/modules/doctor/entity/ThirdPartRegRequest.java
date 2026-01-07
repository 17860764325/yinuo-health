package org.jeecg.modules.doctor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 第三方注册信息DTO（作为接口请求Body的第一层级）
 * 封装接口请求的核心参数，对应原根节点thirdPartRegInfoDTO
 */
@Data
public class ThirdPartRegRequest {
    /**
     * 就诊卡类别：1.就诊卡 2.医保卡 3.身份证 4.体检卡（非必传）
     */
    private Integer cardTypeId;

    /**
     * 医院ID（必传）
     */
    private Integer hospitalId;

    /**
     * 身份证（有年龄、性别时可不传）
     */
    private String idCard;

    /**
     * 婚姻状况（已婚、未婚、其他）（必传）
     */
    private String maritalStatusName;

    /**
     * 人员名称（必传）
     */
    private String name;

    /**
     * 民族（必传）
     */
    private String nationName;

    /**
     * 患者地址（非必传）
     */
    private String patAddress;

    /**
     * 患者年龄（有身份证号可不传）（必传）
     */
    private Integer patAge;

    /**
     * 就诊卡号ID（非必传）
     */
    private Integer patCardId;

    /**
     * 患者就诊卡号（非必传）
     */
    private String patCardNo;

    /**
     * 性别（有身份证号可不传）（必传）
     */
    private String sex;

    /**
     * 手机号（非必传）
     */
    private String telNo;

    /**
     * 项目ID集合（必传）
     */
    private List<ProjectIdDTO> projectIdList;

    /**
     * 是否为绿色通道，0:否 1:是（必传）
     */
    private Integer orderGreenChannel;

    /**
     * 出生日期(yyyy-MM-dd HH:mm:ss)，出生日期或年龄至少传递一个（非必传）
     */
    private String birthDate;

    /**
     * 单位编号、个人体检=0（非必传）
     */
    private Integer companyId;

    /**
     * 分组编号、个人体检=0（非必传）
     */
    private Integer companyGroupId;

    /**
     * 单位体检记录id（非必传）
     */
    private Integer companyRecordId;

    /**
     * 单位分组体检记录id（非必传）
     */
    private Integer companyGroupRecordId;

    /**
     * 部门名称（必传）
     */
    private String department;

    /**
     * VIP标识（原文档未标注是否必传/类型，暂定义为Integer）
     */
    private Integer vipFlag;

    /**
     * 项目ID集合内的子对象
     */
    @Data
    public static class ProjectIdDTO {
        /**
         * 实际价格（必传）
         */
        private BigDecimal acPrice;

        /**
         * 是否套餐项目标识 0 套餐项目 1加项（为空默认为1）（非必传）
         */
        private Integer addLaterFlag;

        /**
         * 药品分类ID（医嘱类型为材料时必传）（非必传）
         */
        private Integer catagoryId;

        /**
         * 药品分类名称（医嘱类型为材料时必传）（非必传）
         */
        private String catagoryName;

        /**
         * 科室id（医嘱类型为材料时必传）（非必传）
         */
        private Integer deptId;

        /**
         * 科室名称（医嘱类型为材料时必传）（非必传）
         */
        private String deptName;

        /**
         * 套餐主键id（非必传）
         */
        private Integer dictPackageId;

        /**
         * 折扣（必传）
         */
        private BigDecimal disc;

        /**
         * 剂量（医嘱类型为材料时必传）（非必传）
         */
        private BigDecimal dose;

        /**
         * 剂量单位（医嘱类型为材料时必传）（非必传）
         */
        private String doseUnit;

        /**
         * 医嘱id（必传）
         */
        private String orderId;

        /**
         * 医嘱名称（必传）
         */
        private String orderName;

        /**
         * 医嘱属性 1 医嘱 2 材料 （不传默认为医嘱）（非必传）
         */
        private Integer orderProp;

        /**
         * 套餐名称（非必传）
         */
        private String packageName;

        /**
         * 医嘱数量（不传默认为1）（非必传）
         */
        private Integer quantity;

        /**
         * 数量单位（医嘱类型为材料时必传）（非必传）
         */
        private String quantityUnit;

        /**
         * 规格（医嘱类型为材料时必传）（非必传）
         */
        private String spec;

        /**
         * 标准价格（必传）
         */
        private BigDecimal stPrice;

        /**
         * 材料字典id（医嘱类型为材料时必传）（原文档标注为"标准价格"，应为笔误）（非必传）
         */
        private Integer ycId;

        /**
         * 材料字典名称（医嘱类型为材料时必传）（非必传）
         */
        private String ycName;
    }
}
