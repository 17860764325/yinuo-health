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
 * @Description: cd_ems_address
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Data
@TableName("cd_ems_address")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cd_ems_address对象", description="cd_ems_address")
public class CdEmsAddress implements Serializable {
    private static final long serialVersionUID = 1L;

	/**patientNo*/
	@Excel(name = "patientNo", width = 15)
    @ApiModelProperty(value = "patientNo")
    private String patientNo;
	/**recipeTakeSort*/
	@Excel(name = "recipeTakeSort", width = 15)
    @ApiModelProperty(value = "recipeTakeSort")
    private Integer recipeTakeSort;
	/**contactPerson*/
	@Excel(name = "contactPerson", width = 15)
    @ApiModelProperty(value = "contactPerson")
    private String contactPerson;
	/**contactPhone*/
	@Excel(name = "contactPhone", width = 15)
    @ApiModelProperty(value = "contactPhone")
    private String contactPhone;
	/**contactAddress*/
	@Excel(name = "contactAddress", width = 15)
    @ApiModelProperty(value = "contactAddress")
    private String contactAddress;
	/**remark*/
	@Excel(name = "remark", width = 15)
    @ApiModelProperty(value = "remark")
    private String remark;
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
}
