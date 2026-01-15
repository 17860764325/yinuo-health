package org.jeecg.modules.doctor.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description: 东华原药品信息实体类
 * @Author: lihaoran
 * @Date: 2026-01-10
 */
@Data
public class DhyDrug {

    /**
     * 药品编号（必填）
     */
    @NotBlank(message = "药品编号不能为空")
    private String drugnum;

    /**
     * 药品名称（必填）
     */
    @NotBlank(message = "药品名称不能为空")
    private String drugname;

    /**
     * 规格（必填）
     */
    @NotBlank(message = "规格不能为空")
    private String drugposition;

    /**
     * 单剂量（必填）
     * 传入kg时请自行换算成g
     */
    @NotNull(message = "单剂量不能为空")
    private BigDecimal drugallnum;

    /**
     * 贴数（必填）
     * 处方贴数(付数/剂数)
     */
    @NotNull(message = "贴数不能为空")
    private Integer tienum;

    /**
     * 总剂量（必填）
     */
    @NotNull(message = "总剂量不能为空")
    private BigDecimal drugweight;

    /**
     * 单位（必填）
     * 剂量与单位请对应
     */
    @NotBlank(message = "单位不能为空")
    private String unit;

    /**
     * 药品脚注
     * 标明是先煎或后下
     */
    private String drugdescription;

    /**
     * 说明
     */
    private String description;

    /**
     * 单价
     * 精确到小数点后两位
     */
    private BigDecimal retailprice;

    /**
     * 批号
     * 药品批号
     */
    private String batchnumber;

    /**
     * 处方一件事编码
     * 上云的医院需要传此编码
     */
    private String midrugcode;
}