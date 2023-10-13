package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lihaoran
 * @date 10/13/23 12:14 AM
 * 创建档案结果接受类
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "创建档案结果接受类", description = "创建档案结果接受类")
public class PersonCreateVo {

    @ApiModelProperty(value = "患者档案id")
    private String patId;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "卡类型编号")
    private String cardType;
}
