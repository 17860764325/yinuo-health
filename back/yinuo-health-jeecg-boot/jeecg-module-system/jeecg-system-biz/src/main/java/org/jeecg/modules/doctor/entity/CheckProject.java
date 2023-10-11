package org.jeecg.modules.doctor.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 项目表
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
@ApiModel(value="check_project对象", description="项目表")
@Data
@TableName("check_project")
public class CheckProject implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**updateBy*/
    @ApiModelProperty(value = "updateBy")
    private String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;
	/**检验项目id*/
	@Excel(name = "检验项目id", width = 15)
    @ApiModelProperty(value = "检验项目id")
    private String labItemId;
	/**检验项目名称*/
	@Excel(name = "检验项目名称", width = 15)
    @ApiModelProperty(value = "检验项目名称")
    private String labName;
	/**报告项目Id*/
	@Excel(name = "报告项目Id", width = 15)
    @ApiModelProperty(value = "报告项目Id")
    private String reportItemId;
	/**样本类型Id*/
	@Excel(name = "样本类型Id", width = 15)
    @ApiModelProperty(value = "样本类型Id")
    private String sampleClassId;
	/**样本类型名称*/
	@Excel(name = "样本类型名称", width = 15)
    @ApiModelProperty(value = "样本类型名称")
    private String sampleClassName;
	/**医嘱字典Id*/
	@Excel(name = "医嘱字典Id", width = 15)
    @ApiModelProperty(value = "医嘱字典Id")
    private String orderId;
	/**医嘱字典名称*/
	@Excel(name = "医嘱字典名称", width = 15)
    @ApiModelProperty(value = "医嘱字典名称")
    private String orderName;
	/**自己系统的项目号*/
	@Excel(name = "自己系统的项目号", width = 15)
    @ApiModelProperty(value = "自己系统的项目号")
    private String mineProjectNo;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String mineItemRemork;

    /**明细信息*/
    @ApiModelProperty(value = "明细信息")
    @TableField(exist = false)
    private List<CheckProjectDetail> itemList;
}
