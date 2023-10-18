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
 * @Description: 条码信息报告信息绑定
 * @Author: jeecg-boot
 * @Date:   2023-10-18
 * @Version: V1.0
 */
@Data
@TableName("lis_apply_bar_code_report_id")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lis_apply_bar_code_report_id对象", description="条码信息报告信息绑定")
public class LisApplyBarCodeReportId implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**体检号*/
	@Excel(name = "体检号", width = 15)
    @ApiModelProperty(value = "体检号")
    private String patientNo;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String patientName;
	/**患者id*/
	@Excel(name = "患者id", width = 15)
    @ApiModelProperty(value = "患者id")
    private String patId;
	/**项目id*/
	@Excel(name = "项目id", width = 15)
    @ApiModelProperty(value = "项目id")
    private String labItemId;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    private String labItemName;
	/**本地项目号*/
	@Excel(name = "本地项目号", width = 15)
    @ApiModelProperty(value = "本地项目号")
    private String itemNo;
	/**条码*/
	@Excel(name = "条码", width = 15)
    @ApiModelProperty(value = "条码")
    private String barCode;
	/**报告id*/
	@Excel(name = "报告id", width = 15)
    @ApiModelProperty(value = "报告id")
    private String reportId;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
	/**预留字段1*/
	@Excel(name = "预留字段1", width = 15)
    @ApiModelProperty(value = "预留字段1")
    private String remark1;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;
	/**updateBy*/
    @ApiModelProperty(value = "updateBy")
    private String updateBy;
}
