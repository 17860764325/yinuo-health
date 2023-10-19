package org.jeecg.modules.doctor.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 报告详情
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Data
@TableName("report_detail")
@ApiModel(value="report_detail对象", description="报告详情")
public class ReportDetail implements Serializable {
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
	/**报告id*/
    @ApiModelProperty(value = "报告id")
    private String reportId;
	/**报告单分组号*/
	@Excel(name = "报告单分组号", width = 15)
    @ApiModelProperty(value = "报告单分组号")
    private String keynoGroup;
	/**明细项目Id*/
	@Excel(name = "明细项目Id", width = 15)
    @ApiModelProperty(value = "明细项目Id")
    private String itemId;
	/**明细项目名称*/
	@Excel(name = "明细项目名称", width = 15)
    @ApiModelProperty(value = "明细项目名称")
    private String itemName;
	/**标准代码*/
	@Excel(name = "标准代码", width = 15)
    @ApiModelProperty(value = "标准代码")
    private String itemNo;
	/**明细项目英文名称*/
	@Excel(name = "明细项目英文名称", width = 15)
    @ApiModelProperty(value = "明细项目英文名称")
    private String itemEname;
	/**参考范围类型*/
	@Excel(name = "参考范围类型", width = 15)
    @ApiModelProperty(value = "参考范围类型")
    private String itemRangeType;
	/**参考范围*/
	@Excel(name = "参考范围", width = 15)
    @ApiModelProperty(value = "参考范围")
    private String itemRange;
	/**参考范围高值*/
	@Excel(name = "参考范围高值", width = 15)
    @ApiModelProperty(value = "参考范围高值")
    private String itemRangeHigh;
	/**参考范围低值*/
	@Excel(name = "参考范围低值", width = 15)
    @ApiModelProperty(value = "参考范围低值")
    private String itemRangeLow;
	/**实验项目编码*/
	@Excel(name = "实验项目编码", width = 15)
    @ApiModelProperty(value = "实验项目编码")
    private String labItemNo;
	/**结果*/
	@Excel(name = "结果", width = 15)
    @ApiModelProperty(value = "结果")
    private String itemResult;
	/**报警标识*/
	@Excel(name = "报警标识", width = 15)
    @ApiModelProperty(value = "报警标识")
    private String mark;
	/**排序号*/
	@Excel(name = "排序号", width = 15)
    @ApiModelProperty(value = "排序号")
    private String sno;
	/**细菌编码*/
	@Excel(name = "细菌编码", width = 15)
    @ApiModelProperty(value = "细菌编码")
    private String germNo;
	/**细菌名称*/
	@Excel(name = "细菌名称", width = 15)
    @ApiModelProperty(value = "细菌名称")
    private String germName;
	/**阴阳性*/
	@Excel(name = "阴阳性", width = 15)
    @ApiModelProperty(value = "阴阳性")
    private String yinYangNature;
	/**专家意见*/
	@Excel(name = "专家意见", width = 15)
    @ApiModelProperty(value = "专家意见")
    private String expertOpinion;
	/**检验结果（阴性）*/
	@Excel(name = "检验结果（阴性）", width = 15)
    @ApiModelProperty(value = "检验结果（阴性）")
    private String filingMemo;
	/**报告备注*/
	@Excel(name = "报告备注", width = 15)
    @ApiModelProperty(value = "报告备注")
    private String reportNotes;
	/**涂片结果（阴性）*/
	@Excel(name = "涂片结果（阴性）", width = 15)
    @ApiModelProperty(value = "涂片结果（阴性）")
    private String smearMemo;
	/**是否危急值报告,0:否,1:是;*/
	@Excel(name = "是否危急值报告,0:否,1:是;", width = 15)
    @ApiModelProperty(value = "是否危急值报告,0:否,1:是;")
    private Integer flagCritical;
	/**结果时间*/
	@Excel(name = "结果时间", width = 15)
    @ApiModelProperty(value = "结果时间")
    private String resultTime;
	/**样式 1:普通 2:手工 3:微生物阴性 4:微生物阳性*/
	@Excel(name = "样式 1:普通 2:手工 3:微生物阴性 4:微生物阳性", width = 15)
    @ApiModelProperty(value = "样式 1:普通 2:手工 3:微生物阴性 4:微生物阳性")
    private String pageType;
	/**样本类型id*/
	@Excel(name = "样本类型id", width = 15)
    @ApiModelProperty(value = "样本类型id")
    private String sampleClassId;
	/**样本类型名称*/
	@Excel(name = "样本类型名称", width = 15)
    @ApiModelProperty(value = "样本类型名称")
    private String sampleClassName;

    /**详情样本id*/
	@Excel(name = "详情样本id", width = 15)
    @ApiModelProperty(value = "详情样本id")
    private String rptDetailId;

}
