package org.jeecg.modules.doctor.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description: 东华原处方信息实体类
 * @Author: lihaoran
 * @Date: 2026-01-10
 */
@Data
public class DhyPrescription {

    /**
     * 委托单号
     */
    private String delnum;

    /**
     * 医院名称（必填）
     */
    @NotBlank(message = "医院名称不能为空")
    private String hospitalname;

    /**
     * 医院唯一标识（必填）
     */
    @NotBlank(message = "医院唯一标识不能为空")
    private String hospitalkey;

    /**
     * 处方号（必填）
     */
    @NotBlank(message = "处方号不能为空")
    private String pspnum;

    /**
     * 患者姓名（必填）
     */
    @NotBlank(message = "患者姓名不能为空")
    private String name;

    /**
     * 性别（必填）
     * 0 保密 1 男 2 女
     * 默认 dhySYSTEM 代表男
     */
    @NotBlank(message = "性别不能为空")
    private String sex;

    /**
     * 年龄（必填）
     * 传入字符串，数字请传入大于0小于130的数字
     */
    @NotBlank(message = "年龄不能为空")
    private String age;

    /**
     * 服用方式（必填）
     * 内服，外用，熏洗，清洗，灌肠，传汉字
     */
    @NotBlank(message = "服用方式不能为空")
    private String takemethod;

    /**
     * 贴数（必填）
     * 传入数字 如 7 贴
     */
    @NotNull(message = "贴数不能为空")
    private Integer dose;

    /**
     * 次数（必填）
     * 一（贴/付/剂）吃几次
     */
    @NotNull(message = "次数不能为空")
    private Integer takenum;

    /**
     * 药品总味数（必填）
     * 该处方一共有多少味药，请传入数字
     */
    @NotNull(message = "药品总味数不能为空")
    private Integer drugcount;

    /**
     * 是否代煎（必填）
     * 传入 dhySYSTEM 表示默认代煎
     * 0 代配(只配药不煎药) 1 代煎
     * 默认 dhySYSTEM
     */
    @NotBlank(message = "是否代煎不能为空")
    private String isdaijian;

    /**
     * 加工类型（必填）
     * 1.汤药 2.丸剂 3.打粉 4.膏方代配 5.膏方 6.水丸 7.蜜丸
     * 8.水蜜丸 9.低温制粉 10.颗粒 11.浓缩水丸 12.浓缩蜜丸 13.散剂
     * 14.大蜜丸 15.小蜜丸 16.茶包 17.打粉100 18.胶囊 19.敷贴
     * 20.封包 21.切片 22.协定丸方 23.四季膏方
     */
    @NotNull(message = "加工类型不能为空")
    private Integer processtype;

    /**
     * 处方类型（必填）
     * 0 是门诊 1 住院
     * 如果是 0 请传入 dhySYSTEM
     * 默认 dhySYSTEM
     */
    @NotBlank(message = "处方类型不能为空")
    private Integer ptype;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 科室
     */
    private String department;

    /**
     * 病区
     */
    private String inpatientarea;

    /**
     * 病房
     */
    private String ward;

    /**
     * 病床
     */
    private String sickbed;

    /**
     * 诊断结果
     */
    private String diagresult;

    /**
     * 取药时间
     * 如果时间格式请传入格式如：2016-03-25 08:00:00
     * 如果不为时间格式请传入对应字符串 如：两天后
     */
    private String getdrugtime;

    /**
     * 取药序号
     */
    private Integer getdrugnum;

    /**
     * 煎药方案
     * 传入编号，具体入参根据项目现场配置决定
     */
    private Integer decscheme;

    /**
     * 包装量
     * 请填入数字如 200，150,100
     * (包装量请传入大于等于50并且小于等280)
     */
    private Integer packagenum;

    /**
     * 操作人员
     */
    private String doperson;

    /**
     * 收件方
     * 发快递必传
     */
    private String dtbcompany;

    /**
     * 收件地址的省份
     * 发快递必传
     */
    private String dtbprovince;

    /**
     * 收件地址的市
     * 发快递必传
     */
    private String dtbcity;

    /**
     * 收件地址的县
     * 发快递必传
     */
    private String dtbcounty;

    /**
     * 收件地址的详细地址
     * 发快递必传
     */
    private String dtbaddress;

    /**
     * 联系电话
     */
    private String dtbphone;

    /**
     * 快递类型
     * 默认传 dhySYSTEM 代表厂内发货其他传入编号
     * 0：厂内配送（默认）1：顺丰 2：圆通 3：中通 4：EMS 5：其他
     * 6:速必达 7.顺丰同城 8.京东 9.顺丰医寄通 10.东方汇
     * 11.上海邮政同城 14.邮政速递便民通 15.上海邮政同城药配
     * 16.吉通 98.微医 18.德邦 19.杭报安心送 20.叮当快递
     * 默认 dhySYSTEM
     */
    private Integer dtbtype;

    /**
     * 浸泡加水量（ml）
     * 默认 2000 如：2000
     * 默认 dhySYSTEM
     */
    private Integer soakwater;

    /**
     * 浸泡时间（分钟）
     * 默认 30
     */
    private Integer soaktime;

    /**
     * 标签数量
     * 如有请传值，最好不要为0
     */
    private Integer labelnum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 医生
     */
    private String doctor;

    /**
     * 医生脚注
     */
    private String footnote;

    /**
     * 煎药方式
     * 传入 dhySYSTEM 默认为无
     * 其他请传入编号 1：先煎 2：后下 3：加糖加蜜 4.先煎和后下 5.浓煎
     * 默认 dhySYSTEM
     */
    private String decmothed;

    /**
     * 服用方法
     * 传入 dhySYSTEM 默认为无
     * 其他请传入编号 1：水煎餐前 2：水煎餐后
     * 默认 dhySYSTEM
     */
    private String takeway;

    /**
     * 备注A
     */
    private String remarka;

    /**
     * 备注B
     */
    private String remarkb;

    /**
     * 快递支付方式
     * 1 寄方付 2 收方付 3 第三方付
     */
    private String payment;

    /**
     * 医嘱
     */
    private String yizhu;

    /**
     * 金额
     * 传入时可精确到小数点后两位，如：152.00
     */
    private BigDecimal money;

    /**
     * 社保号、医保号、健康卡号
     */
    private String healthcardno;

    /**
     * 传方序号
     */
    private String outpatientindex;

    /**
     * 病历号、病案号
     */
    private String casenumber;

    /**
     * 住院号、门诊号
     */
    private String outpatientnumber;

    /**
     * 跳过流程
     * 0: 不跳过 1：跳过调剂 2：跳过调剂和复核
     */
    private Integer procedurejump;

    /**
     * 病人档案
     * 传 PDF 或图片
     */
    private String patientfile;

    /**
     * 档案类型
     * 0.无(默认) 1.图片 2.PDF
     */
    private Integer filetype;

    /**
     * 特殊处方
     * 1.正常 2.克隆方 3.协定方
     */
    private Integer isrepetition;

    /**
     * 特殊群体
     * 1 成人，2 孕妇，3 儿童 4 老年人，0 保密
     */
    private Integer particular;

    /**
     * 身份验证
     * 对接验证扩展字段(新增)，加密方式咨询接口提供方
     */
    private String token;

    /**
     * 急煎
     * 0 否，1 急煎
     */
    private Integer isurgent;

    /**
     * 医院处方号
     */
    private String hospitalpspnum;

    /**
     * 开方时间
     * 时间格式请传入格式如：2016-03-25 08:00:00
     */
    private String prescribetime;

    /**
     * 调剂类型
     * 0 人工调剂，1 自动化设备调剂
     */
    private Integer dispensemode;

    /**
     * 运单号
     * 快递运单号，非煎药系统下单，在煎药系统查询并打印面单时需要传此参数
     */
    private String mailno;
}