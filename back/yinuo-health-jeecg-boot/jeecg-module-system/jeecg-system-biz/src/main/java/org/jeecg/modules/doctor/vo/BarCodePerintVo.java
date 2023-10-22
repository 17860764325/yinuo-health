package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lihaoran
 * @date 2023/10/22 20:51
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "条码打印数据类", description = "条码打印数据类")
public class BarCodePerintVo {

    @ApiModelProperty("条码号")
    private String barCode;

    @ApiModelProperty("试管颜色")
    private String tubeColor;

    @ApiModelProperty("样本类型名称")
    private String sampleClassName;

    @ApiModelProperty("体检号")
    private String patientNo;

    @ApiModelProperty("姓名")
    private String patientName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("科室")
    private String department;

    @ApiModelProperty("患者类型")
    private String patType;

    @ApiModelProperty("项目编码")
    private String labItemNo;

    @ApiModelProperty("项目名称")
    private String labItemName;

}
