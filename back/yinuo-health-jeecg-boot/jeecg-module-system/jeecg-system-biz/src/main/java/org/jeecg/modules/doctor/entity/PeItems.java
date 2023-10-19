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
 * @Description: 项目详情
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Data
@TableName("pe_items")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pe_items对象", description="项目详情")
public class PeItems implements Serializable {
    private static final long serialVersionUID = 1L;

	/**itemNo*/
	@Excel(name = "itemNo", width = 15)
    @ApiModelProperty(value = "itemNo")
    private String itemNo;
	/**orderSort*/
	@Excel(name = "orderSort", width = 15)
    @ApiModelProperty(value = "orderSort")
    private Integer orderSort;
	/**itemName*/
	@Excel(name = "itemName", width = 15)
    @ApiModelProperty(value = "itemName")
    private String itemName;
	/**departmentId*/
	@Excel(name = "departmentId", width = 15)
    @ApiModelProperty(value = "departmentId")
    private Integer departmentId;
	/**resultClassId*/
	@Excel(name = "resultClassId", width = 15)
    @ApiModelProperty(value = "resultClassId")
    private Integer resultClassId;
	/**script*/
	@Excel(name = "script", width = 15)
    @ApiModelProperty(value = "script")
    private String script;
	/**normalValue*/
	@Excel(name = "normalValue", width = 15)
    @ApiModelProperty(value = "normalValue")
    private String normalValue;
	/**units*/
	@Excel(name = "units", width = 15)
    @ApiModelProperty(value = "units")
    private String units;
	/**remark*/
	@Excel(name = "remark", width = 15)
    @ApiModelProperty(value = "remark")
    private String remark;
	/**upperLimit*/
	@Excel(name = "upperLimit", width = 15)
    @ApiModelProperty(value = "upperLimit")
    private Double upperLimit;
	/**lowerLimit*/
	@Excel(name = "lowerLimit", width = 15)
    @ApiModelProperty(value = "lowerLimit")
    private Double lowerLimit;
	/**upperInfo*/
	@Excel(name = "upperInfo", width = 15)
    @ApiModelProperty(value = "upperInfo")
    private String upperInfo;
	/**lowerInfo*/
	@Excel(name = "lowerInfo", width = 15)
    @ApiModelProperty(value = "lowerInfo")
    private String lowerInfo;
	/**sexLimit*/
	@Excel(name = "sexLimit", width = 15)
    @ApiModelProperty(value = "sexLimit")
    private Integer sexLimit;
	/**inputCode*/
	@Excel(name = "inputCode", width = 15)
    @ApiModelProperty(value = "inputCode")
    private String inputCode;
	/**isActive*/
	@Excel(name = "isActive", width = 15)
    @ApiModelProperty(value = "isActive")
    private String isActive;
	/**interfaceNo*/
	@Excel(name = "interfaceNo", width = 15)
    @ApiModelProperty(value = "interfaceNo")
    private String interfaceNo;
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
	/**defaultScript*/
	@Excel(name = "defaultScript", width = 15)
    @ApiModelProperty(value = "defaultScript")
    private String defaultScript;
	/**isNotNull*/
	@Excel(name = "isNotNull", width = 15)
    @ApiModelProperty(value = "isNotNull")
    private String isNotNull;
	/**visibleScript*/
	@Excel(name = "visibleScript", width = 15)
    @ApiModelProperty(value = "visibleScript")
    private String visibleScript;
}
