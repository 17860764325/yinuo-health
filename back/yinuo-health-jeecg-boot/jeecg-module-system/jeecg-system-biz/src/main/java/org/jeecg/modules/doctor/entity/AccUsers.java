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
 * @Description: acc_users
 * @Author: jeecg-boot
 * @Date:   2026-01-15
 * @Version: V1.0
 */
@Data
@TableName("acc_users")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="acc_users对象", description="acc_users")
public class AccUsers implements Serializable {
    private static final long serialVersionUID = 1L;

	/**serialNo*/
	@Excel(name = "serialNo", width = 15)
    @ApiModelProperty(value = "serialNo")
    private Integer serialNo;
	/**loginName*/
	@Excel(name = "loginName", width = 15)
    @ApiModelProperty(value = "loginName")
    private String loginName;
	/**userName*/
	@Excel(name = "userName", width = 15)
    @ApiModelProperty(value = "userName")
    private String userName;
	/**description*/
	@Excel(name = "description", width = 15)
    @ApiModelProperty(value = "description")
    private String description;
	/**password*/
	@Excel(name = "password", width = 15)
    @ApiModelProperty(value = "password")
    private String password;
	/**lastLoginTime*/
	@Excel(name = "lastLoginTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "lastLoginTime")
    private Date lastLoginTime;
	/**lastLoginIp*/
	@Excel(name = "lastLoginIp", width = 15)
    @ApiModelProperty(value = "lastLoginIp")
    private String lastLoginIp;
	/**status*/
	@Excel(name = "status", width = 15)
    @ApiModelProperty(value = "status")
    private Integer status;
	/**hidenUser*/
	@Excel(name = "hidenUser", width = 15)
    @ApiModelProperty(value = "hidenUser")
    private String hidenUser;
	/**systemUser*/
	@Excel(name = "systemUser", width = 15)
    @ApiModelProperty(value = "systemUser")
    private String systemUser;
	/**cardNo*/
	@Excel(name = "cardNo", width = 15)
    @ApiModelProperty(value = "cardNo")
    private String cardNo;
	/**mustModifyPassword*/
	@Excel(name = "mustModifyPassword", width = 15)
    @ApiModelProperty(value = "mustModifyPassword")
    private String mustModifyPassword;
	/**accountStop*/
	@Excel(name = "accountStop", width = 15)
    @ApiModelProperty(value = "accountStop")
    private String accountStop;
	/**canNotModifyPassword*/
	@Excel(name = "canNotModifyPassword", width = 15)
    @ApiModelProperty(value = "canNotModifyPassword")
    private String canNotModifyPassword;
	/**accountLock*/
	@Excel(name = "accountLock", width = 15)
    @ApiModelProperty(value = "accountLock")
    private String accountLock;
	/**inputCode*/
	@Excel(name = "inputCode", width = 15)
    @ApiModelProperty(value = "inputCode")
    private String inputCode;
	/**isDeveloper*/
	@Excel(name = "isDeveloper", width = 15)
    @ApiModelProperty(value = "isDeveloper")
    private String isDeveloper;
	/**isSupper*/
	@Excel(name = "isSupper", width = 15)
    @ApiModelProperty(value = "isSupper")
    private String isSupper;
	/**imeName*/
	@Excel(name = "imeName", width = 15)
    @ApiModelProperty(value = "imeName")
    private String imeName;
	/**klybInt*/
	@Excel(name = "klybInt", width = 15)
    @ApiModelProperty(value = "klybInt")
    private Integer klybInt;
	/**klybLxInt*/
	@Excel(name = "klybLxInt", width = 15)
    @ApiModelProperty(value = "klybLxInt")
    private Integer klybLxInt;
	/**inputCodeCompareMode*/
	@Excel(name = "inputCodeCompareMode", width = 15)
    @ApiModelProperty(value = "inputCodeCompareMode")
    private Integer inputCodeCompareMode;
	/**serviceStar*/
	@Excel(name = "serviceStar", width = 15)
    @ApiModelProperty(value = "serviceStar")
    private Integer serviceStar;
	/**zgylLoginName*/
	@Excel(name = "zgylLoginName", width = 15)
    @ApiModelProperty(value = "zgylLoginName")
    private String zgylLoginName;
	/**zgylPassword*/
	@Excel(name = "zgylPassword", width = 15)
    @ApiModelProperty(value = "zgylPassword")
    private String zgylPassword;
	/**czjmLoginName*/
	@Excel(name = "czjmLoginName", width = 15)
    @ApiModelProperty(value = "czjmLoginName")
    private String czjmLoginName;
	/**czjmPassword*/
	@Excel(name = "czjmPassword", width = 15)
    @ApiModelProperty(value = "czjmPassword")
    private String czjmPassword;
	/**gsLoginName*/
	@Excel(name = "gsLoginName", width = 15)
    @ApiModelProperty(value = "gsLoginName")
    private String gsLoginName;
	/**gsPassword*/
	@Excel(name = "gsPassword", width = 15)
    @ApiModelProperty(value = "gsPassword")
    private String gsPassword;
	/**nhLoginName*/
	@Excel(name = "nhLoginName", width = 15)
    @ApiModelProperty(value = "nhLoginName")
    private String nhLoginName;
	/**nhPassword*/
	@Excel(name = "nhPassword", width = 15)
    @ApiModelProperty(value = "nhPassword")
    private String nhPassword;
	/**lastUpdateOperator*/
	@Excel(name = "lastUpdateOperator", width = 15)
    @ApiModelProperty(value = "lastUpdateOperator")
    private Integer lastUpdateOperator;
	/**lastUpdateStation*/
	@Excel(name = "lastUpdateStation", width = 15)
    @ApiModelProperty(value = "lastUpdateStation")
    private Integer lastUpdateStation;
	/**lastUpdateVersion*/
	@Excel(name = "lastUpdateVersion", width = 15)
    @ApiModelProperty(value = "lastUpdateVersion")
    private Integer lastUpdateVersion;
	/**lastUpdateTime*/
	@Excel(name = "lastUpdateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "lastUpdateTime")
    private Date lastUpdateTime;
	/**kpLoginName*/
	@Excel(name = "kpLoginName", width = 15)
    @ApiModelProperty(value = "kpLoginName")
    private String kpLoginName;
	/**kpPassword*/
	@Excel(name = "kpPassword", width = 15)
    @ApiModelProperty(value = "kpPassword")
    private String kpPassword;
}
