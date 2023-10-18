package org.jeecg.modules.doctor.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lihaoran
 * @date 10/16/23 11:49 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "查询患者检验的项目", description = "查询患者检验的项目")
public class PatItems {

    @ApiModelProperty("体检号 ")
    private String patientNo;

    @ApiModelProperty("姓名 ")
    private String patientName;

    @ApiModelProperty("患者类型（本地系统） ")
    private String assayCause;

    @ApiModelProperty("项目名称 ")
    private String itemName;

    @ApiModelProperty("项目编码 ")
    private String composeItemNo;

    @ApiModelProperty("项目编码2 ")
    private String cureNo;

    @ApiModelProperty("条码号")
    private String barCode;
}
