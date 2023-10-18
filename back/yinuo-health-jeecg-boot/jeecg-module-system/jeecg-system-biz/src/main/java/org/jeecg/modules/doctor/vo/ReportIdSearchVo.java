package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lihaoran
 * @date 2023/10/19 00:39
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "报告id查询", description = "报告id查询")
public class ReportIdSearchVo {

    @ApiModelProperty("报告Id")
    private String reportId;

    @ApiModelProperty("报告单分组号id")
    private String keynoGroup;


    @ApiModelProperty("条码号")
    private String barCode;

    @ApiModelProperty("患者ID")
    private String patId;

    @ApiModelProperty("患者姓名")
    private String patName;

    @ApiModelProperty("科室")
    private String patDeptName;

    @ApiModelProperty("住院id")
    private String patInHosId;

    @ApiModelProperty("患者类型（1门诊、2住院、3体检，4绿色通道，5合作单位)")
    private String patType;

    @ApiModelProperty("报告日期，审核日期")
    private String reportTime;


    @ApiModelProperty("是否是微生物报告,0:否,1:是;")
    private String flagGerm;

    @ApiModelProperty("报告备注")
    private String reportNotes;

    @ApiModelProperty("检验项目列表")
    private String testList;

    @ApiModelProperty("检验医师")
    private String testUser;


    @ApiModelProperty("核对医师")
    private String checkUser;

    @ApiModelProperty("检验结果")
    private String filingMemo;

    @ApiModelProperty("涂片结果")
    private String smearMemo;


    @ApiModelProperty("危急值报告标识")
    private String criticalFlag;

    @ApiModelProperty("是否是多重耐药菌")
    private String multiDrugResistance;

    @ApiModelProperty("样本类型id")
    private String sampleClassId;

    @ApiModelProperty("样本类型名称")
    private String sampleClassName;

}
