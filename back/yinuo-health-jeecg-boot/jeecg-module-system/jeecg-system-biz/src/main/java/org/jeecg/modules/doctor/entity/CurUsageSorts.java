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
 * @Description: cur_usage_sorts
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Data
@TableName("cur_usage_sorts")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cur_usage_sorts对象", description="cur_usage_sorts")
public class CurUsageSorts implements Serializable {
    private static final long serialVersionUID = 1L;

	/**usageId*/
	@Excel(name = "usageId", width = 15)
    @ApiModelProperty(value = "usageId")
    private Integer usageId;
	/**usageTypeId*/
	@Excel(name = "usageTypeId", width = 15)
    @ApiModelProperty(value = "usageTypeId")
    private Integer usageTypeId;
	/**usageModeId*/
	@Excel(name = "usageModeId", width = 15)
    @ApiModelProperty(value = "usageModeId")
    private Integer usageModeId;
	/**abbreviation*/
	@Excel(name = "abbreviation", width = 15)
    @ApiModelProperty(value = "abbreviation")
    private String abbreviation;
	/**chineseName*/
	@Excel(name = "chineseName", width = 15)
    @ApiModelProperty(value = "chineseName")
    private String chineseName;
	/**addtionInfo*/
	@Excel(name = "addtionInfo", width = 15)
    @ApiModelProperty(value = "addtionInfo")
    private String addtionInfo;
	/**inputCode*/
	@Excel(name = "inputCode", width = 15)
    @ApiModelProperty(value = "inputCode")
    private String inputCode;
	/**needCharge*/
	@Excel(name = "needCharge", width = 15)
    @ApiModelProperty(value = "needCharge")
    private String needCharge;
	/**clinicHiden*/
	@Excel(name = "clinicHiden", width = 15)
    @ApiModelProperty(value = "clinicHiden")
    private String clinicHiden;
	/**inhouseHiden*/
	@Excel(name = "inhouseHiden", width = 15)
    @ApiModelProperty(value = "inhouseHiden")
    private String inhouseHiden;
	/**clinicInfusion*/
	@Excel(name = "clinicInfusion", width = 15)
    @ApiModelProperty(value = "clinicInfusion")
    private String clinicInfusion;
	/**isActive*/
	@Excel(name = "isActive", width = 15)
    @ApiModelProperty(value = "isActive")
    private String isActive;
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
	/**privateMode*/
	@Excel(name = "privateMode", width = 15)
    @ApiModelProperty(value = "privateMode")
    private Integer privateMode;
}
