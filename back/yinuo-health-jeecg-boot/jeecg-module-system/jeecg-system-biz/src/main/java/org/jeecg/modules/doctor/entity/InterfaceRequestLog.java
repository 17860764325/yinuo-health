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
 * @Description: 人员提交信息
 * @Author: jeecg-boot
 * @Date:   2023-10-11
 * @Version: V1.0
 */
@Data
@TableName("interface_request_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="interface_request_log对象", description="人员提交信息")
public class InterfaceRequestLog implements Serializable {
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
	/**接口编码*/
	@Excel(name = "接口编码", width = 15)
    @ApiModelProperty(value = "接口编码")
    private String interfaceCode;
	/**接口名称*/
	@Excel(name = "接口名称", width = 15)
    @ApiModelProperty(value = "接口名称")
    private String interfaceName;
    /**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String patientName;
	/**具体人员体检号*/
	@Excel(name = "具体人员体检号", width = 15)
    @ApiModelProperty(value = "具体人员体检号")
    private String patientNo;
	/**患者 id*/
	@Excel(name = "患者 id", width = 15)
    @ApiModelProperty(value = "患者 id")
    private String patId;
	/**请求是否成功*/
	@Excel(name = "请求是否成功", width = 15)
    @ApiModelProperty(value = "请求是否成功")
    private String success;
	/**请求信息*/
	@Excel(name = "请求信息", width = 15)
    @ApiModelProperty(value = "请求信息")
    private String sendMessage;
	/**接收信息*/
	@Excel(name = "接收信息", width = 15)
    @ApiModelProperty(value = "接收信息")
    private String receiveMessage;
	/**log*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
}
