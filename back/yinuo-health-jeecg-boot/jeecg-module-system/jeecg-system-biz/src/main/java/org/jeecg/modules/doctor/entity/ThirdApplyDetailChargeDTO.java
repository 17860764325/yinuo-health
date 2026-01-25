package org.jeecg.modules.doctor.entity;

import lombok.Data;

/**
 * 计价明细实体类
 * @author 开发者
 * @date 2026-01-25
 */
@Data
public class ThirdApplyDetailChargeDTO {
    /** 计价字典主键，默认值：8004015041017414660 */
    private Long chargeId = 8004015041017414660L;

    /** 计价名称，默认值：X线摄影成像 */
    private String chargeName = "X线摄影成像";

    /** 实际支付金额，默认值：55 */
    private Integer getAmount = 55;

    /** 医嘱字典主键ID，默认值：8020206121549432068 */
    private Long orderId = 8020206121549432068L;

    /** 医嘱名称，默认值：胸部正位拍片（胸部） */
    private String orderName = "胸部正位拍片（胸部）";

    /** 单价，默认值：55 */
    private Integer price = 55;

    /** 数量，默认值：1 */
    private Integer quantity = 1;
}