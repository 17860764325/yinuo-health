package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lihaoran
 * @date 10/16/23 8:52 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "LIS检验项目申请信息", description = "LIS检验项目申请信息")
public class LISApplyInfo {

    @ApiModelProperty(value = "患者id")
    private List<String> patIds;

    @ApiModelProperty(value = "患者类型")
    private String patType;

}
