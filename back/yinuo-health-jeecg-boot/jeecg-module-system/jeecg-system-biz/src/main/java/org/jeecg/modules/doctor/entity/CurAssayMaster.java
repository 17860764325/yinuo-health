package org.jeecg.modules.doctor.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 人员检查项目表
 * @Author: jeecg-boot
 * @Date:   2023-10-18
 * @Version: V1.0
 */
@Data
@TableName("cur_assay_master")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cur_assay_master对象", description="人员检查项目表")
public class CurAssayMaster implements Serializable {
    private static final long serialVersionUID = 1L;

	/**assayNo*/
	@Excel(name = "assayNo", width = 15)
    @ApiModelProperty(value = "assayNo")
    private String assayNo;
	/**barcode*/
	@Excel(name = "barcode", width = 15)
    @ApiModelProperty(value = "barcode")
    private String barcode;
	/**bookingFlag*/
	@Excel(name = "bookingFlag", width = 15)
    @ApiModelProperty(value = "bookingFlag")
    private String bookingFlag;
	/**patientSource*/
	@Excel(name = "patientSource", width = 15)
    @ApiModelProperty(value = "patientSource")
    private Integer patientSource;
	/**hospPkno*/
	@Excel(name = "hospPkno", width = 15)
    @ApiModelProperty(value = "hospPkno")
    private String hospPkno;
	/**patientNo*/
	@Excel(name = "patientNo", width = 15)
    @ApiModelProperty(value = "patientNo")
    private String patientNo;
	/**caseNo*/
	@Excel(name = "caseNo", width = 15)
    @ApiModelProperty(value = "caseNo")
    private String caseNo;
	/**patientName*/
	@Excel(name = "patientName", width = 15)
    @ApiModelProperty(value = "patientName")
    private String patientName;
	/**sex*/
	@Excel(name = "sex", width = 15)
    @ApiModelProperty(value = "sex")
    private String sex;
	/**age*/
	@Excel(name = "age", width = 15)
    @ApiModelProperty(value = "age")
    private Integer age;
	/**fareIdentity*/
	@Excel(name = "fareIdentity", width = 15)
    @ApiModelProperty(value = "fareIdentity")
    private String fareIdentity;
	/**companyId*/
	@Excel(name = "companyId", width = 15)
    @ApiModelProperty(value = "companyId")
    private Integer companyId;
	/**diagnoseName*/
	@Excel(name = "diagnoseName", width = 15)
    @ApiModelProperty(value = "diagnoseName")
    private String diagnoseName;
	/**personNo*/
	@Excel(name = "personNo", width = 15)
    @ApiModelProperty(value = "personNo")
    private String personNo;
	/**bedName*/
	@Excel(name = "bedName", width = 15)
    @ApiModelProperty(value = "bedName")
    private String bedName;
	/**inputCode*/
	@Excel(name = "inputCode", width = 15)
    @ApiModelProperty(value = "inputCode")
    private String inputCode;
	/**address*/
	@Excel(name = "address", width = 15)
    @ApiModelProperty(value = "address")
    private String address;
	/**ihDepartmentId*/
	@Excel(name = "ihDepartmentId", width = 15)
    @ApiModelProperty(value = "ihDepartmentId")
    private Integer ihDepartmentId;
	/**departmentId*/
	@Excel(name = "departmentId", width = 15)
    @ApiModelProperty(value = "departmentId")
    private Integer departmentId;
	/**currWardId*/
	@Excel(name = "currWardId", width = 15)
    @ApiModelProperty(value = "currWardId")
    private Integer currWardId;
	/**doctorId*/
	@Excel(name = "doctorId", width = 15)
    @ApiModelProperty(value = "doctorId")
    private Integer doctorId;
	/**workNo*/
	@Excel(name = "workNo", width = 15)
    @ApiModelProperty(value = "workNo")
    private String workNo;
	/**assaySortId*/
	@Excel(name = "assaySortId", width = 15)
    @ApiModelProperty(value = "assaySortId")
    private Integer assaySortId;
	/**assayModeId*/
	@Excel(name = "assayModeId", width = 15)
    @ApiModelProperty(value = "assayModeId")
    private Integer assayModeId;
	/**priorityIndicator*/
	@Excel(name = "priorityIndicator", width = 15)
    @ApiModelProperty(value = "priorityIndicator")
    private Integer priorityIndicator;
	/**specimenId*/
	@Excel(name = "specimenId", width = 15)
    @ApiModelProperty(value = "specimenId")
    private Integer specimenId;
	/**specimenQuantity*/
	@Excel(name = "specimenQuantity", width = 15)
    @ApiModelProperty(value = "specimenQuantity")
    private String specimenQuantity;
	/**specimenNote*/
	@Excel(name = "specimenNote", width = 15)
    @ApiModelProperty(value = "specimenNote")
    private String specimenNote;
	/**specimenCollectDate*/
	@Excel(name = "specimenCollectDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "specimenCollectDate")
    private Date specimenCollectDate;
	/**specimenCollectOperatorId*/
	@Excel(name = "specimenCollectOperatorId", width = 15)
    @ApiModelProperty(value = "specimenCollectOperatorId")
    private Integer specimenCollectOperatorId;
	/**specimenInceptId*/
	@Excel(name = "specimenInceptId", width = 15)
    @ApiModelProperty(value = "specimenInceptId")
    private Integer specimenInceptId;
	/**specimenInceptDate*/
	@Excel(name = "specimenInceptDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "specimenInceptDate")
    private Date specimenInceptDate;
	/**specimenInceptOperatorId*/
	@Excel(name = "specimenInceptOperatorId", width = 15)
    @ApiModelProperty(value = "specimenInceptOperatorId")
    private Integer specimenInceptOperatorId;
	/**specimenStatus*/
	@Excel(name = "specimenStatus", width = 15)
    @ApiModelProperty(value = "specimenStatus")
    private Integer specimenStatus;
	/**bookingDate*/
	@Excel(name = "bookingDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "bookingDate")
    private Date bookingDate;
	/**assayCause*/
	@Excel(name = "assayCause", width = 15)
    @ApiModelProperty(value = "assayCause")
    private String assayCause;
	/**itemName*/
	@Excel(name = "itemName", width = 15)
    @ApiModelProperty(value = "itemName")
    private String itemName;
	/**totalFee*/
	@Excel(name = "totalFee", width = 15)
    @ApiModelProperty(value = "totalFee")
    private String totalFee;
	/**chargeMode*/
	@Excel(name = "chargeMode", width = 15)
    @ApiModelProperty(value = "chargeMode")
    private Integer chargeMode;
	/**chargeFlag*/
	@Excel(name = "chargeFlag", width = 15)
    @ApiModelProperty(value = "chargeFlag")
    private String chargeFlag;
	/**specimenChargeFlag*/
	@Excel(name = "specimenChargeFlag", width = 15)
    @ApiModelProperty(value = "specimenChargeFlag")
    private String specimenChargeFlag;
	/**specimenChargeDate*/
	@Excel(name = "specimenChargeDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "specimenChargeDate")
    private Date specimenChargeDate;
	/**operatorId*/
	@Excel(name = "operatorId", width = 15)
    @ApiModelProperty(value = "operatorId")
    private Integer operatorId;
	/**operateDate*/
	@Excel(name = "operateDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "operateDate")
    private Date operateDate;
	/**procDepartmentId*/
	@Excel(name = "procDepartmentId", width = 15)
    @ApiModelProperty(value = "procDepartmentId")
    private Integer procDepartmentId;
	/**procOperatorId*/
	@Excel(name = "procOperatorId", width = 15)
    @ApiModelProperty(value = "procOperatorId")
    private Integer procOperatorId;
	/**procDate*/
	@Excel(name = "procDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "procDate")
    private Date procDate;
	/**checkOperatorId*/
	@Excel(name = "checkOperatorId", width = 15)
    @ApiModelProperty(value = "checkOperatorId")
    private Integer checkOperatorId;
	/**checkDate*/
	@Excel(name = "checkDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "checkDate")
    private Date checkDate;
	/**abateOperatorId*/
	@Excel(name = "abateOperatorId", width = 15)
    @ApiModelProperty(value = "abateOperatorId")
    private Integer abateOperatorId;
	/**abateDate*/
	@Excel(name = "abateDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "abateDate")
    private Date abateDate;
	/**doctorAdviceId*/
	@Excel(name = "doctorAdviceId", width = 15)
    @ApiModelProperty(value = "doctorAdviceId")
    private Integer doctorAdviceId;
	/**status*/
	@Excel(name = "status", width = 15)
    @ApiModelProperty(value = "status")
    private Integer status;
	/**hasReport*/
	@Excel(name = "hasReport", width = 15)
    @ApiModelProperty(value = "hasReport")
    private String hasReport;
	/**arrangeOperatorId*/
	@Excel(name = "arrangeOperatorId", width = 15)
    @ApiModelProperty(value = "arrangeOperatorId")
    private Integer arrangeOperatorId;
	/**arrangeFlag*/
	@Excel(name = "arrangeFlag", width = 15)
    @ApiModelProperty(value = "arrangeFlag")
    private String arrangeFlag;
	/**arrangeDate*/
	@Excel(name = "arrangeDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "arrangeDate")
    private Date arrangeDate;
	/**arrangeOperateDate*/
	@Excel(name = "arrangeOperateDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "arrangeOperateDate")
    private Date arrangeOperateDate;
	/**lastUpdateOperator*/
	@Excel(name = "lastUpdateOperator", width = 15)
    @ApiModelProperty(value = "lastUpdateOperator")
    private Integer lastUpdateOperator;
	/**lastUpdateStation*/
	@Excel(name = "lastUpdateStation", width = 15)
    @ApiModelProperty(value = "lastUpdateStation")
    private Integer lastUpdateStation;
	/**lastUpdateVersion*/
	@Excel(name = "lastUpdateVersion", width = 15)
    @ApiModelProperty(value = "lastUpdateVersion")
    private Integer lastUpdateVersion;
	/**lastUpdateTime*/
	@Excel(name = "lastUpdateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "lastUpdateTime")
    private Date lastUpdateTime;
	/**chargeSortId*/
	@Excel(name = "chargeSortId", width = 15)
    @ApiModelProperty(value = "chargeSortId")
    private Integer chargeSortId;
	/**isClinicSpecialDiagnose*/
	@Excel(name = "isClinicSpecialDiagnose", width = 15)
    @ApiModelProperty(value = "isClinicSpecialDiagnose")
    private String isClinicSpecialDiagnose;
	/**isCardCharge*/
	@Excel(name = "isCardCharge", width = 15)
    @ApiModelProperty(value = "isCardCharge")
    private String isCardCharge;
	/**isLock*/
	@Excel(name = "isLock", width = 15)
    @ApiModelProperty(value = "isLock")
    private String isLock;
	/**transFlag*/
	@Excel(name = "transFlag", width = 15)
    @ApiModelProperty(value = "transFlag")
    private String transFlag;
	/**transNo*/
	@Excel(name = "transNo", width = 15)
    @ApiModelProperty(value = "transNo")
    private String transNo;
	/**lockDepartmentId*/
	@Excel(name = "lockDepartmentId", width = 15)
    @ApiModelProperty(value = "lockDepartmentId")
    private Integer lockDepartmentId;
	/**lockModuleId*/
	@Excel(name = "lockModuleId", width = 15)
    @ApiModelProperty(value = "lockModuleId")
    private Integer lockModuleId;
	/**lockOperatorId*/
	@Excel(name = "lockOperatorId", width = 15)
    @ApiModelProperty(value = "lockOperatorId")
    private Integer lockOperatorId;
	/**lockRemark*/
	@Excel(name = "lockRemark", width = 15)
    @ApiModelProperty(value = "lockRemark")
    private String lockRemark;
	/**lockTime*/
	@Excel(name = "lockTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "lockTime")
    private Date lockTime;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**reportOperatorId*/
	@Excel(name = "reportOperatorId", width = 15)
    @ApiModelProperty(value = "reportOperatorId")
    private Integer reportOperatorId;
	/**reportDate*/
	@Excel(name = "reportDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "reportDate")
    private Date reportDate;
	/**众阳系统返回的条码号*/
	@Excel(name = "众阳系统返回的条码号", width = 15)
    @ApiModelProperty(value = "众阳系统返回的条码号")
    private String barCode;
}
