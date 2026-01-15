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
 * @Description: cur_recipe_list
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Data
@TableName("cur_recipe_list")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cur_recipe_list对象", description="cur_recipe_list")
public class CurRecipeList implements Serializable {
    private static final long serialVersionUID = 1L;

	/**recipeListId*/
	@Excel(name = "recipeListId", width = 15)
    @ApiModelProperty(value = "recipeListId")
    private Integer recipeListId;
	/**patientSource*/
	@Excel(name = "patientSource", width = 15)
    @ApiModelProperty(value = "patientSource")
    private Integer patientSource;
	/**recipeNo*/
	@Excel(name = "recipeNo", width = 15)
    @ApiModelProperty(value = "recipeNo")
    private String recipeNo;
	/**groupNo*/
	@Excel(name = "groupNo", width = 15)
    @ApiModelProperty(value = "groupNo")
    private String groupNo;
	/**itemIndex*/
	@Excel(name = "itemIndex", width = 15)
    @ApiModelProperty(value = "itemIndex")
    private Integer itemIndex;
	/**drgRecipeSortId*/
	@Excel(name = "drgRecipeSortId", width = 15)
    @ApiModelProperty(value = "drgRecipeSortId")
    private Integer drgRecipeSortId;
	/**recipeSortId*/
	@Excel(name = "recipeSortId", width = 15)
    @ApiModelProperty(value = "recipeSortId")
    private Integer recipeSortId;
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
    private Integer fareIdentity;
	/**weight*/
	@Excel(name = "weight", width = 15)
    @ApiModelProperty(value = "weight")
    private Double weight;
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
	/**currWardId*/
	@Excel(name = "currWardId", width = 15)
    @ApiModelProperty(value = "currWardId")
    private Integer currWardId;
	/**departmentId*/
	@Excel(name = "departmentId", width = 15)
    @ApiModelProperty(value = "departmentId")
    private Integer departmentId;
	/**doctorId*/
	@Excel(name = "doctorId", width = 15)
    @ApiModelProperty(value = "doctorId")
    private Integer doctorId;
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
	/**chargeDate*/
	@Excel(name = "chargeDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "chargeDate")
    private Date chargeDate;
	/**infusionDepartmentId*/
	@Excel(name = "infusionDepartmentId", width = 15)
    @ApiModelProperty(value = "infusionDepartmentId")
    private Integer infusionDepartmentId;
	/**infusionStatus*/
	@Excel(name = "infusionStatus", width = 15)
    @ApiModelProperty(value = "infusionStatus")
    private Integer infusionStatus;
	/**infusionProcOperator*/
	@Excel(name = "infusionProcOperator", width = 15)
    @ApiModelProperty(value = "infusionProcOperator")
    private Integer infusionProcOperator;
	/**infusionProcDate*/
	@Excel(name = "infusionProcDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "infusionProcDate")
    private Date infusionProcDate;
	/**infusionAbateOperator*/
	@Excel(name = "infusionAbateOperator", width = 15)
    @ApiModelProperty(value = "infusionAbateOperator")
    private Integer infusionAbateOperator;
	/**infusionAbateDate*/
	@Excel(name = "infusionAbateDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "infusionAbateDate")
    private Date infusionAbateDate;
	/**procDepartmentId*/
	@Excel(name = "procDepartmentId", width = 15)
    @ApiModelProperty(value = "procDepartmentId")
    private Integer procDepartmentId;
	/**procOperatorId*/
	@Excel(name = "procOperatorId", width = 15)
    @ApiModelProperty(value = "procOperatorId")
    private Integer procOperatorId;
	/**checkOperatorId*/
	@Excel(name = "checkOperatorId", width = 15)
    @ApiModelProperty(value = "checkOperatorId")
    private Integer checkOperatorId;
	/**procDate*/
	@Excel(name = "procDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "procDate")
    private Date procDate;
	/**inoutNo*/
	@Excel(name = "inoutNo", width = 15)
    @ApiModelProperty(value = "inoutNo")
    private String inoutNo;
	/**itemNo*/
	@Excel(name = "itemNo", width = 15)
    @ApiModelProperty(value = "itemNo")
    private String itemNo;

    	/**usage*/
	@Excel(name = "usage", width = 15)
    @ApiModelProperty(value = "usage")
    private String usage;
	/**itemName*/
	@Excel(name = "itemName", width = 15)
    @ApiModelProperty(value = "itemName")
    private String itemName;
	/**drgSpec*/
	@Excel(name = "drgSpec", width = 15)
    @ApiModelProperty(value = "drgSpec")
    private String drgSpec;
	/**drgFormSort*/
	@Excel(name = "drgFormSort", width = 15)
    @ApiModelProperty(value = "drgFormSort")
    private String drgFormSort;
	/**mediSortId*/
	@Excel(name = "mediSortId", width = 15)
    @ApiModelProperty(value = "mediSortId")
    private Integer mediSortId;
	/**中药付数*/
	@Excel(name = "中药付数", width = 15)
    @ApiModelProperty(value = "中药付数")
    private Integer herbsTimes;
	/**drgProductorId*/
	@Excel(name = "drgProductorId", width = 15)
    @ApiModelProperty(value = "drgProductorId")
    private Integer drgProductorId;
	/**amount*/
	@Excel(name = "amount", width = 15)
    @ApiModelProperty(value = "amount")
    private BigDecimal amount;
	/**unit*/
	@Excel(name = "unit", width = 15)
    @ApiModelProperty(value = "unit")
    private String unit;
	/**price*/
	@Excel(name = "price", width = 15)
    @ApiModelProperty(value = "price")
    private String price;
	/**retailPrice*/
	@Excel(name = "retailPrice", width = 15)
    @ApiModelProperty(value = "retailPrice")
    private String retailPrice;
	/**totalFee*/
	@Excel(name = "totalFee", width = 15)
    @ApiModelProperty(value = "totalFee")
    private String totalFee;
	/**dosage*/
	@Excel(name = "dosage", width = 15)
    @ApiModelProperty(value = "dosage")
    private String dosage;
	/**singleUseAmount*/
	@Excel(name = "singleUseAmount", width = 15)
    @ApiModelProperty(value = "singleUseAmount")
    private BigDecimal singleUseAmount;
	/**singleUseUnits*/
	@Excel(name = "singleUseUnits", width = 15)
    @ApiModelProperty(value = "singleUseUnits")
    private String singleUseUnits;
	/**usageSortId*/
	@Excel(name = "usageSortId", width = 15)
    @ApiModelProperty(value = "usageSortId")
    private Integer usageSortId;
	/**frequencySortId*/
	@Excel(name = "frequencySortId", width = 15)
    @ApiModelProperty(value = "frequencySortId")
    private Integer frequencySortId;
	/**perPackRetailAmount*/
	@Excel(name = "perPackRetailAmount", width = 15)
    @ApiModelProperty(value = "perPackRetailAmount")
    private Integer perPackRetailAmount;
	/**perRetailMinAmount*/
	@Excel(name = "perRetailMinAmount", width = 15)
    @ApiModelProperty(value = "perRetailMinAmount")
    private Integer perRetailMinAmount;
	/**doctorAdviceId*/
	@Excel(name = "doctorAdviceId", width = 15)
    @ApiModelProperty(value = "doctorAdviceId")
    private Integer doctorAdviceId;
	/**chargeMode*/
	@Excel(name = "chargeMode", width = 15)
    @ApiModelProperty(value = "chargeMode")
    private Integer chargeMode;
	/**charged*/
	@Excel(name = "charged", width = 15)
    @ApiModelProperty(value = "charged")
    private String charged;
	/**ihDrawNo*/
	@Excel(name = "ihDrawNo", width = 15)
    @ApiModelProperty(value = "ihDrawNo")
    private String ihDrawNo;
	/**drawPersonName*/
	@Excel(name = "drawPersonName", width = 15)
    @ApiModelProperty(value = "drawPersonName")
    private String drawPersonName;
	/**drawPersonNo*/
	@Excel(name = "drawPersonNo", width = 15)
    @ApiModelProperty(value = "drawPersonNo")
    private String drawPersonNo;
	/**status*/
	@Excel(name = "status", width = 15)
    @ApiModelProperty(value = "status")
    private Integer status;
	/**chargeSortId*/
	@Excel(name = "chargeSortId", width = 15)
    @ApiModelProperty(value = "chargeSortId")
    private Integer chargeSortId;
	/**backRatifier*/
	@Excel(name = "backRatifier", width = 15)
    @ApiModelProperty(value = "backRatifier")
    private String backRatifier;
	/**backReason*/
	@Excel(name = "backReason", width = 15)
    @ApiModelProperty(value = "backReason")
    private String backReason;
	/**remark*/
	@Excel(name = "remark", width = 15)
    @ApiModelProperty(value = "remark")
    private String remark;
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
	/**isCardCharge*/
	@Excel(name = "isCardCharge", width = 15)
    @ApiModelProperty(value = "isCardCharge")
    private String isCardCharge;
	/**isTreatRecipe*/
	@Excel(name = "isTreatRecipe", width = 15)
    @ApiModelProperty(value = "isTreatRecipe")
    private String isTreatRecipe;
	/**treatSortId*/
	@Excel(name = "treatSortId", width = 15)
    @ApiModelProperty(value = "treatSortId")
    private Integer treatSortId;
	/**treatNo*/
	@Excel(name = "treatNo", width = 15)
    @ApiModelProperty(value = "treatNo")
    private String treatNo;
	/**isLock*/
	@Excel(name = "isLock", width = 15)
    @ApiModelProperty(value = "isLock")
    private String isLock;
	/**isClinicSpecialDiagnose*/
	@Excel(name = "isClinicSpecialDiagnose", width = 15)
    @ApiModelProperty(value = "isClinicSpecialDiagnose")
    private String isClinicSpecialDiagnose;
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
	/**transFlag*/
	@Excel(name = "transFlag", width = 15)
    @ApiModelProperty(value = "transFlag")
    private String transFlag;
	/**transNo*/
	@Excel(name = "transNo", width = 15)
    @ApiModelProperty(value = "transNo")
    private String transNo;
	/**infusionNo*/
	@Excel(name = "infusionNo", width = 15)
    @ApiModelProperty(value = "infusionNo")
    private String infusionNo;
	/**skinDrugMinus*/
	@Excel(name = "skinDrugMinus", width = 15)
    @ApiModelProperty(value = "skinDrugMinus")
    private Integer skinDrugMinus;
	/**drgAcceptNo*/
	@Excel(name = "drgAcceptNo", width = 15)
    @ApiModelProperty(value = "drgAcceptNo")
    private String drgAcceptNo;
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
	/**orderProcDate*/
	@Excel(name = "orderProcDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "orderProcDate")
    private Date orderProcDate;
	/**skinTestResult*/
	@Excel(name = "skinTestResult", width = 15)
    @ApiModelProperty(value = "skinTestResult")
    private Integer skinTestResult;
	/**secretContent*/
	@Excel(name = "secretContent", width = 15)
    @ApiModelProperty(value = "secretContent")
    private String secretContent;
	/**commissionPersonName*/
	@Excel(name = "commissionPersonName", width = 15)
    @ApiModelProperty(value = "commissionPersonName")
    private String commissionPersonName;
	/**commissionPersonNo*/
	@Excel(name = "commissionPersonNo", width = 15)
    @ApiModelProperty(value = "commissionPersonNo")
    private String commissionPersonNo;
	/**commissionPhone*/
	@Excel(name = "commissionPhone", width = 15)
    @ApiModelProperty(value = "commissionPhone")
    private String commissionPhone;
	/**selfPayPercent*/
	@Excel(name = "selfPayPercent", width = 15)
    @ApiModelProperty(value = "selfPayPercent")
    private Double selfPayPercent;
	/**isOutsideHospital*/
	@Excel(name = "isOutsideHospital", width = 15)
    @ApiModelProperty(value = "isOutsideHospital")
    private String isOutsideHospital;
	/**rxTraceCode*/
	@Excel(name = "rxTraceCode", width = 15)
    @ApiModelProperty(value = "rxTraceCode")
    private String rxTraceCode;
	/**hiRxNo*/
	@Excel(name = "hiRxNo", width = 15)
    @ApiModelProperty(value = "hiRxNo")
    private String hiRxNo;
	/**recipeCheckDate*/
	@Excel(name = "recipeCheckDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "recipeCheckDate")
    private Date recipeCheckDate;
	/**recipeCheckOperatorId*/
	@Excel(name = "recipeCheckOperatorId", width = 15)
    @ApiModelProperty(value = "recipeCheckOperatorId")
    private Integer recipeCheckOperatorId;
	/**recipeCheckStatus*/
	@Excel(name = "recipeCheckStatus", width = 15)
    @ApiModelProperty(value = "recipeCheckStatus")
    private Integer recipeCheckStatus;
	/**recipeUncheckDate*/
	@Excel(name = "recipeUncheckDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "recipeUncheckDate")
    private Date recipeUncheckDate;
	/**recipeUncheckOperatorId*/
	@Excel(name = "recipeUncheckOperatorId", width = 15)
    @ApiModelProperty(value = "recipeUncheckOperatorId")
    private Integer recipeUncheckOperatorId;
}
