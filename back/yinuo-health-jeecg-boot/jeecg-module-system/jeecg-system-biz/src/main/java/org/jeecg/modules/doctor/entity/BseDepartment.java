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
 * @Description: bse_department
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Data
@TableName("bse_department")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bse_department对象", description="bse_department")
public class BseDepartment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**serialNo*/
	@Excel(name = "serialNo", width = 15)
    @ApiModelProperty(value = "serialNo")
    private Integer serialNo;
	/**departmentCode*/
	@Excel(name = "departmentCode", width = 15)
    @ApiModelProperty(value = "departmentCode")
    private String departmentCode;
	/**departmentName*/
	@Excel(name = "departmentName", width = 15)
    @ApiModelProperty(value = "departmentName")
    private String departmentName;
	/**shortName*/
	@Excel(name = "shortName", width = 15)
    @ApiModelProperty(value = "shortName")
    private String shortName;
	/**description*/
	@Excel(name = "description", width = 15)
    @ApiModelProperty(value = "description")
    private String description;
	/**departmentSortId*/
	@Excel(name = "departmentSortId", width = 15)
    @ApiModelProperty(value = "departmentSortId")
    private Integer departmentSortId;
	/**departmentAttrId*/
	@Excel(name = "departmentAttrId", width = 15)
    @ApiModelProperty(value = "departmentAttrId")
    private String departmentAttrId;
	/**functions*/
	@Excel(name = "functions", width = 15)
    @ApiModelProperty(value = "functions")
    private Integer functions;
	/**manager*/
	@Excel(name = "manager", width = 15)
    @ApiModelProperty(value = "manager")
    private String manager;
	/**telphone*/
	@Excel(name = "telphone", width = 15)
    @ApiModelProperty(value = "telphone")
    private String telphone;
	/**inputCode*/
	@Excel(name = "inputCode", width = 15)
    @ApiModelProperty(value = "inputCode")
    private String inputCode;
	/**isActive*/
	@Excel(name = "isActive", width = 15)
    @ApiModelProperty(value = "isActive")
    private String isActive;
	/**klybMzInt*/
	@Excel(name = "klybMzInt", width = 15)
    @ApiModelProperty(value = "klybMzInt")
    private String klybMzInt;
	/**klybZyInt*/
	@Excel(name = "klybZyInt", width = 15)
    @ApiModelProperty(value = "klybZyInt")
    private String klybZyInt;
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
	/**guidInfo*/
	@Excel(name = "guidInfo", width = 15)
    @ApiModelProperty(value = "guidInfo")
    private String guidInfo;
	/**isOnlySelfOrder*/
	@Excel(name = "isOnlySelfOrder", width = 15)
    @ApiModelProperty(value = "isOnlySelfOrder")
    private Integer isOnlySelfOrder;
}
