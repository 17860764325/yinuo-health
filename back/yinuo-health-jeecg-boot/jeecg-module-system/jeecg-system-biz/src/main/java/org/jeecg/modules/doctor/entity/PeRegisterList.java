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
 * @Description: 人员信息查询
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
@Data
@TableName("pe_register_list")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pe_register_list对象", description="人员信息查询")
public class PeRegisterList implements Serializable {
    private static final long serialVersionUID = 1L;

	/**hospPkno*/
	@Excel(name = "hospPkno", width = 15)
    @ApiModelProperty(value = "hospPkno")
    private String hospPkno;
	/**patientNo*/
	@Excel(name = "patientNo", width = 15)
    @ApiModelProperty(value = "patientNo")
    private String patientNo;
	/**cardNo*/
	@Excel(name = "cardNo", width = 15)
    @ApiModelProperty(value = "cardNo")
    private String cardNo;
	/**peTimes*/
	@Excel(name = "peTimes", width = 15)
    @ApiModelProperty(value = "peTimes")
    private Integer peTimes;
	/**rePeFlag*/
	@Excel(name = "rePeFlag", width = 15)
    @ApiModelProperty(value = "rePeFlag")
    private String rePeFlag;
	/**companyFlag*/
	@Excel(name = "companyFlag", width = 15)
    @ApiModelProperty(value = "companyFlag")
    private String companyFlag;
	/**companyId*/
	@Excel(name = "companyId", width = 15)
    @ApiModelProperty(value = "companyId")
    private Integer companyId;
	/**companyGroupId*/
	@Excel(name = "companyGroupId", width = 15)
    @ApiModelProperty(value = "companyGroupId")
    private Integer companyGroupId;
	/**peDate*/
	@Excel(name = "peDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "peDate")
    private Date peDate;
	/**peTypeId*/
	@Excel(name = "peTypeId", width = 15)
    @ApiModelProperty(value = "peTypeId")
    private Integer peTypeId;
	/**bookingDate*/
	@Excel(name = "bookingDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "bookingDate")
    private Date bookingDate;
	/**patientName*/
	@Excel(name = "patientName", width = 15)
    @ApiModelProperty(value = "patientName")
    private String patientName;
	/**sex*/
	@Excel(name = "sex", width = 15)
    @ApiModelProperty(value = "sex")
    private String sex;
	/**birthday*/
	@Excel(name = "birthday", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "birthday")
    private Date birthday;
	/**age*/
	@Excel(name = "age", width = 15)
    @ApiModelProperty(value = "age")
    private Integer age;
	/**fareIdentity*/
	@Excel(name = "fareIdentity", width = 15)
    @ApiModelProperty(value = "fareIdentity")
    private Integer fareIdentity;
	/**personNo*/
	@Excel(name = "personNo", width = 15)
    @ApiModelProperty(value = "personNo")
    private String personNo;
	/**marriageStatus*/
	@Excel(name = "marriageStatus", width = 15)
    @ApiModelProperty(value = "marriageStatus")
    private Integer marriageStatus;
	/**occupation*/
	@Excel(name = "occupation", width = 15)
    @ApiModelProperty(value = "occupation")
    private String occupation;
	/**address*/
	@Excel(name = "address", width = 15)
    @ApiModelProperty(value = "address")
    private String address;
	/**telphone*/
	@Excel(name = "telphone", width = 15)
    @ApiModelProperty(value = "telphone")
    private String telphone;
	/**email*/
	@Excel(name = "email", width = 15)
    @ApiModelProperty(value = "email")
    private String email;
	/**nationId*/
	@Excel(name = "nationId", width = 15)
    @ApiModelProperty(value = "nationId")
    private Integer nationId;
	/**extend1*/
	@Excel(name = "extend1", width = 15)
    @ApiModelProperty(value = "extend1")
    private String extend1;
	/**extend2*/
	@Excel(name = "extend2", width = 15)
    @ApiModelProperty(value = "extend2")
    private String extend2;
	/**extend3*/
	@Excel(name = "extend3", width = 15)
    @ApiModelProperty(value = "extend3")
    private String extend3;
	/**chargeFlag*/
	@Excel(name = "chargeFlag", width = 15)
    @ApiModelProperty(value = "chargeFlag")
    private String chargeFlag;
	/**totalFee*/
	@Excel(name = "totalFee", width = 15)
    @ApiModelProperty(value = "totalFee")
    private String totalFee;
	/**status*/
	@Excel(name = "status", width = 15)
    @ApiModelProperty(value = "status")
    private Integer status;
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
	/**chiefSumUp*/
	@Excel(name = "chiefSumUp", width = 15)
    @ApiModelProperty(value = "chiefSumUp")
    private String chiefSumUp;
	/**chiefAdvice*/
	@Excel(name = "chiefAdvice", width = 15)
    @ApiModelProperty(value = "chiefAdvice")
    private String chiefAdvice;
	/**chiefCheckDate*/
	@Excel(name = "chiefCheckDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "chiefCheckDate")
    private Date chiefCheckDate;
	/**chiefDoctorId*/
	@Excel(name = "chiefDoctorId", width = 15)
    @ApiModelProperty(value = "chiefDoctorId")
    private Integer chiefDoctorId;
	/**chiefOperatorId*/
	@Excel(name = "chiefOperatorId", width = 15)
    @ApiModelProperty(value = "chiefOperatorId")
    private Integer chiefOperatorId;
	/**healthStatusId*/
	@Excel(name = "healthStatusId", width = 15)
    @ApiModelProperty(value = "healthStatusId")
    private Integer healthStatusId;
	/**countercheckDate*/
	@Excel(name = "countercheckDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "countercheckDate")
    private Date countercheckDate;
	/**countercheckAfterDays*/
	@Excel(name = "countercheckAfterDays", width = 15)
    @ApiModelProperty(value = "countercheckAfterDays")
    private Integer countercheckAfterDays;
	/**reportTemplateId*/
	@Excel(name = "reportTemplateId", width = 15)
    @ApiModelProperty(value = "reportTemplateId")
    private Integer reportTemplateId;
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
	/**signBeforeDate*/
	@Excel(name = "signBeforeDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "signBeforeDate")
    private Date signBeforeDate;
	/**signBeforeOperatorId*/
	@Excel(name = "signBeforeOperatorId", width = 15)
    @ApiModelProperty(value = "signBeforeOperatorId")
    private Integer signBeforeOperatorId;
	/**signAfterDate*/
	@Excel(name = "signAfterDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "signAfterDate")
    private Date signAfterDate;
	/**signAfterOperatorId*/
	@Excel(name = "signAfterOperatorId", width = 15)
    @ApiModelProperty(value = "signAfterOperatorId")
    private Integer signAfterOperatorId;
	/**adrAddr*/
	@Excel(name = "adrAddr", width = 15)
    @ApiModelProperty(value = "adrAddr")
    private String adrAddr;
	/**adrAttribute*/
	@Excel(name = "adrAttribute", width = 15)
    @ApiModelProperty(value = "adrAttribute")
    private String adrAttribute;
	/**adrCity*/
	@Excel(name = "adrCity", width = 15)
    @ApiModelProperty(value = "adrCity")
    private String adrCity;
	/**adrCountrySide*/
	@Excel(name = "adrCountrySide", width = 15)
    @ApiModelProperty(value = "adrCountrySide")
    private String adrCountrySide;
	/**adrCountryTown*/
	@Excel(name = "adrCountryTown", width = 15)
    @ApiModelProperty(value = "adrCountryTown")
    private String adrCountryTown;
	/**adrProvince*/
	@Excel(name = "adrProvince", width = 15)
    @ApiModelProperty(value = "adrProvince")
    private String adrProvince;
	/**adrVillage*/
	@Excel(name = "adrVillage", width = 15)
    @ApiModelProperty(value = "adrVillage")
    private String adrVillage;
	/**education*/
	@Excel(name = "education", width = 15)
    @ApiModelProperty(value = "education")
    private String education;
	/**oppositePatientName*/
	@Excel(name = "oppositePatientName", width = 15)
    @ApiModelProperty(value = "oppositePatientName")
    private String oppositePatientName;
	/**oppositePatientNo*/
	@Excel(name = "oppositePatientNo", width = 15)
    @ApiModelProperty(value = "oppositePatientNo")
    private String oppositePatientNo;
	/**printDate*/
	@Excel(name = "printDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "printDate")
    private Date printDate;
	/**printOperatorId*/
	@Excel(name = "printOperatorId", width = 15)
    @ApiModelProperty(value = "printOperatorId")
    private Integer printOperatorId;
	/**printReportFlag*/
	@Excel(name = "printReportFlag", width = 15)
    @ApiModelProperty(value = "printReportFlag")
    private String printReportFlag;
	/**printTimes*/
	@Excel(name = "printTimes", width = 15)
    @ApiModelProperty(value = "printTimes")
    private Integer printTimes;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**branchHospitalId*/
	@Excel(name = "branchHospitalId", width = 15)
    @ApiModelProperty(value = "branchHospitalId")
    private Integer branchHospitalId;
	/**transFlag*/
	@Excel(name = "transFlag", width = 15)
    @ApiModelProperty(value = "transFlag")
    private String transFlag;
	/**isSuccess*/
	@Excel(name = "isSuccess", width = 15)
    @ApiModelProperty(value = "isSuccess")
    private Integer isSuccess;

    /**众阳系统的患者 id*/
	@Excel(name = "patId", width = 15)
    @ApiModelProperty(value = "patId")
    private String patId;

    /**众阳系统的患者 类型*/
	@Excel(name = "patType", width = 15)
    @ApiModelProperty(value = "patType")
    private String patType;

    /**是否已经提交LIS检验申请*/
	@Excel(name = "isLisApply", width = 15)
    @ApiModelProperty(value = "isLisApply")
    private String isLisApply;

    /**是否已经条码生成*/
	@Excel(name = "isBarCodeBuild", width = 15)
    @ApiModelProperty(value = "isBarCodeBuild")
    private String isBarCodeBuild;

    /**是否已经查询过报告*/
	@Excel(name = "isReport", width = 15)
    @ApiModelProperty(value = "isReport")
    private String isReport;
}
