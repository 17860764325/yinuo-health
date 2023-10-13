package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lihaoran
 * @date 10/11/23 3:22 PM
 * 档案查询人员数据
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "档案查询人员数据", description = "档案查询人员数据")
public class PersonSearchVo {

    @ApiModelProperty(value = "姓名")
    private String patName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "证件类型编号")
    private String idCardType;

    @ApiModelProperty(value = "证件号")
    private String idCardNo;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "患者手机号")
    private String phone;

    @ApiModelProperty(value = "卡类型编号")
    private String cardType;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "名族编号")
    private String nation;

    @ApiModelProperty(value = "名族名称")
    private String nationName;

    @ApiModelProperty(value = "国家编号")
    private String country;

    @ApiModelProperty(value = "国家名称")
    private String countryName;

    @ApiModelProperty(value = "省份编码")
    private String province;

    @ApiModelProperty(value = "省份名称")
    private String provinceName;

    @ApiModelProperty(value = "城市编码")
    private String city;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "区/县编码")
    private String town;

    @ApiModelProperty(value = "区/县名称")
    private String townName;

    @ApiModelProperty(value = "详细地址")
    private String addr;

    @ApiModelProperty(value = "费别系统编码")
    private String chargeClassId;

    @ApiModelProperty(value = "费别系统名称")
    private String chargeClassName;

    @ApiModelProperty(value = "患者 id")
    private String patId;

}
