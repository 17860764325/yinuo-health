package org.jeecg.modules.doctor.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
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
 * @Description: 人员检查结果信息表
 * @Author: jeecg-boot
 * @Date: 2023-10-19
 * @Version: V1.0
 */
@Data
@TableName("pe_report_department_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "pe_report_department_detail对象", description = "人员检查结果信息表")
public class PeReportDepartmentDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * patientNo
     */
    @Excel(name = "patientNo", width = 15)
    @ApiModelProperty(value = "patientNo")
    @TableField("patient_no")
    private String patientNo;
    /**
     * departmentId
     */
    @Excel(name = "departmentId", width = 15)
    @ApiModelProperty(value = "departmentId")
    @TableField("department_id")
    private Integer departmentId;
    /**
     * composeItemNo
     */
    @Excel(name = "composeItemNo", width = 15)
    @ApiModelProperty(value = "composeItemNo")
    @TableField("compose_item_no")
    private String composeItemNo;
    /**
     * itemNo
     */
    @Excel(name = "itemNo", width = 15)
    @ApiModelProperty(value = "itemNo")
    @TableField("item_no")
    private String itemNo;
    /**
     * resultClassId
     */
    @Excel(name = "resultClassId", width = 15)
    @ApiModelProperty(value = "resultClassId")
    private Integer resultClassId;
    /**
     * peResult
     */
    @Excel(name = "peResult", width = 15)
    @ApiModelProperty(value = "peResult")
    private String peResult;
    /**
     * isAbnormity
     */
    @Excel(name = "isAbnormity", width = 15)
    @ApiModelProperty(value = "isAbnormity")
    private String isAbnormity;
    /**
     * abortReasonId
     */
    @Excel(name = "abortReasonId", width = 15)
    @ApiModelProperty(value = "abortReasonId")
    private Integer abortReasonId;
    /**
     * operatorId
     */
    @Excel(name = "operatorId", width = 15)
    @ApiModelProperty(value = "operatorId")
    private Integer operatorId;
    /**
     * operateDate
     */
    @Excel(name = "operateDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "operateDate")
    private Date operateDate;
    /**
     * resultFlag
     */
    @Excel(name = "resultFlag", width = 15)
    @ApiModelProperty(value = "resultFlag")
    private Integer resultFlag;
    /**
     * normalUp
     */
    @Excel(name = "normalUp", width = 15)
    @ApiModelProperty(value = "normalUp")
    private Double normalUp;
    /**
     * normalDown
     */
    @Excel(name = "normalDown", width = 15)
    @ApiModelProperty(value = "normalDown")
    private Double normalDown;
    /**
     * normalPrint
     */
    @Excel(name = "normalPrint", width = 15)
    @ApiModelProperty(value = "normalPrint")
    private String normalPrint;
    /**
     * reportOperatorId
     */
    @Excel(name = "reportOperatorId", width = 15)
    @ApiModelProperty(value = "reportOperatorId")
    private Integer reportOperatorId;
    /**
     * reportCheckOperatorId
     */
    @Excel(name = "reportCheckOperatorId", width = 15)
    @ApiModelProperty(value = "reportCheckOperatorId")
    private Integer reportCheckOperatorId;
    /**
     * reportDate
     */
    @Excel(name = "reportDate", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "reportDate")
    private Date reportDate;
}
