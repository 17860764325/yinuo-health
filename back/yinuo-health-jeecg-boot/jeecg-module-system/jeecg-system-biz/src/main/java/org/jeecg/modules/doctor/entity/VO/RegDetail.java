package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;

/**
 * @author lihaoran
 * @date 2025/9/19 23:47
 */

/**
 * 登记费用明细实体类
 */
@Data
public class RegDetail {

    /**
     * 医嘱id
     */
    private Integer orderId;

    /**
     * 医嘱名称
     */
    private String orderName;

    /**
     * 计价id
     */
    private Integer chargeId;

    /**
     * 计价名称
     */
    private String chargeName;

    /**
     * 登记id
     */
    private Long registrationId;

    /**
     * 登记费用明细id
     */
    private Long regDetailId;

    /**
     * 护嘱id
     */
    private Integer inNurseOrderId;

    /**
     * 门诊发票明细流水id
     */
    private String outDetailchargeId;

    /**
     * 订单id（门诊体检患者取此字段）
     */
    private String outTradeOrderId;

    /**
     * 住院执行医嘱id（住院患者取此字段）
     */
    private String inExeTradeId;
}
