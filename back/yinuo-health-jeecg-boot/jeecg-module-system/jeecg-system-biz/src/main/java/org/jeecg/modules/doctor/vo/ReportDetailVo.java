package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lihaoran
 * @date 2023/10/19 16:11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "报告详情查询", description = "报告详情查询")
public class ReportDetailVo {

    @ApiModelProperty("报告Id")
    private String reportId;

    @ApiModelProperty("报告单分组号")
    private String keynoGroup;

    @ApiModelProperty("详情项目id")
    private String rptDetailId;

    @ApiModelProperty("明细项目Id")
    private String itemId;

    @ApiModelProperty("明细项目名称")
    private String itemName;


    @ApiModelProperty("标准代码")
    private String itemNo;

    @ApiModelProperty("明细项目英文名称")
    private String itemEname;

    @ApiModelProperty("参考范围类型")
    private String itemRangeType;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("参考范围")
    private String itemRange;

    @ApiModelProperty("参考范围高值")
    private String itemRangeHigh;


    @ApiModelProperty("参考范围低值")
    private String itemRangeLow;

    @ApiModelProperty("实验项目编码")
    private String labItemNo;

    @ApiModelProperty("结果")
    private String itemResult;


    @ApiModelProperty("报警标识")
    private String mark;


    @ApiModelProperty("排序号")
    private String sno;


    @ApiModelProperty("细菌编码")
    private String germNo;


    @ApiModelProperty("细菌名称")
    private String germName;


    @ApiModelProperty("阴阳性")
    private String yinYangNature;


    @ApiModelProperty("专家意见")
    private String expertOpinion;


    @ApiModelProperty("检验结果（阴性）")
    private String filingMemo;


    @ApiModelProperty("报告备注")
    private String reportNotes;


    @ApiModelProperty("涂片结果（阴性）")
    private String smearMemo;


    @ApiModelProperty("是否危急值报告,0:否,1:是;")
    private Integer flagCritical;


    @ApiModelProperty("结果时间")
    private String resultTime;

    @ApiModelProperty("样式 1:普通 2:手工 3:微生物阴性 4:微生物阳性")
    private String pageType;


    @ApiModelProperty("样本类型id")
    private String sampleClassId;


    @ApiModelProperty("样本类型名称")
    private String sampleClassName;



}
