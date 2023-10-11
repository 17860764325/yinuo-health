package org.jeecg.modules.doctor.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 项目详情表
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
@ApiModel(value="check_project_detail对象", description="项目详情表")
@Data
@TableName("check_project_detail")
public class CheckProjectDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
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
	/**明细项目Id*/
	@Excel(name = "明细项目Id", width = 15)
    @ApiModelProperty(value = "明细项目Id")
    private String itemId;
	/**明细项目编码*/
	@Excel(name = "明细项目编码", width = 15)
    @ApiModelProperty(value = "明细项目编码")
    private String itemNo;
	/**明细项目名称*/
	@Excel(name = "明细项目名称", width = 15)
    @ApiModelProperty(value = "明细项目名称")
    private String itemName;
	/**自己系统项目的标识*/
	@Excel(name = "自己系统项目的标识", width = 15)
    @ApiModelProperty(value = "自己系统项目的标识")
    private String mineProjectNo;
	/**自己系统项目的标识备注*/
	@Excel(name = "自己系统项目的标识备注", width = 15)
    @ApiModelProperty(value = "自己系统项目的标识备注")
    private Integer mineProjectRemock;
	/**主项目id*/
    @ApiModelProperty(value = "主项目id")
    private String checkProjectId;
}
