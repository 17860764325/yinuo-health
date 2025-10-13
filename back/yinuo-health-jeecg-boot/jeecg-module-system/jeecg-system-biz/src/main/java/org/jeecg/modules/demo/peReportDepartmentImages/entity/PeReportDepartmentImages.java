package org.jeecg.modules.demo.peReportDepartmentImages.entity;

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
 * @Description: pe_report_department_images
 * @Author: jeecg-boot
 * @Date:   2025-10-13
 * @Version: V1.0
 */
@Data
@TableName("pe_report_department_images")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pe_report_department_images对象", description="pe_report_department_images")
public class PeReportDepartmentImages implements Serializable {
    private static final long serialVersionUID = 1L;

	/**patientNo*/
	@Excel(name = "patientNo", width = 15)
    @ApiModelProperty(value = "patientNo")
    private String patientNo;
	/**departmentId*/
	@Excel(name = "departmentId", width = 15)
    @ApiModelProperty(value = "departmentId")
    private Integer departmentId;
	/**imageIndex*/
	@Excel(name = "imageIndex", width = 15)
    @ApiModelProperty(value = "imageIndex")
    private Integer imageIndex;
		/**imageData*/
	@Excel(name = "imageData", width = 15)
    private transient String imageDataString;

   	/**imageData - 二进制图片数据*/
	@Excel(name = "imageData", width = 15)
    @ApiModelProperty(value = "imageData")
    private byte[] imageData;


    // 如果需要前端展示，可以提供Base64编码的getter方法
    public String getBaseSixFour() {
        if (imageData == null) {
            return null;
        }
        // 使用Hutool的Base64工具类进行编码
        return cn.hutool.core.codec.Base64.encode(imageData);
    }

	/**operatorId*/
	@Excel(name = "operatorId", width = 15)
    @ApiModelProperty(value = "operatorId")
    private Integer operatorId;
	/**operateDate*/
	@Excel(name = "operateDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "operateDate")
    private Date operateDate;
	/**billNo*/
	@Excel(name = "billNo", width = 15)
    @ApiModelProperty(value = "billNo")
    private String billNo;
	/**itemName*/
	@Excel(name = "itemName", width = 15)
    @ApiModelProperty(value = "itemName")
    private String itemName;
	/**format*/
	@Excel(name = "format", width = 15)
    @ApiModelProperty(value = "format")
    private String format;
}
