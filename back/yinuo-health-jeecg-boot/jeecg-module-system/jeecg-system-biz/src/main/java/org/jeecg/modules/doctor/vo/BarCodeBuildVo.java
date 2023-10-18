package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lihaoran
 * @date 10/18/23 12:17 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "条码生成接收类", description = "条码生成接收类")
public class BarCodeBuildVo {

    @ApiModelProperty(value = "条码号")
    private String barcode;

    @ApiModelProperty(value = "患者姓名")
    private String patName;


    @ApiModelProperty(value = "患者性别")
    private String patSex;

    @ApiModelProperty(value = "患者年龄")
    private String patAge;


    @ApiModelProperty(value = "试管颜色")
    private String tubeColor;

    @ApiModelProperty(value = "条码生成时间")
    private String createTime;

    @ApiModelProperty(value = "样本类型")
    private String sampleClassName;

    @ApiModelProperty(value = "患者卡号")
    private String patNo;

    @ApiModelProperty(value = "患者Id")
    private String patId;

    @ApiModelProperty(value = "检验项目名称集合")
    private List<String> labItemName;

    @ApiModelProperty(value = "检验项目Id集合")
    private List<String> labItemIdList;

}
