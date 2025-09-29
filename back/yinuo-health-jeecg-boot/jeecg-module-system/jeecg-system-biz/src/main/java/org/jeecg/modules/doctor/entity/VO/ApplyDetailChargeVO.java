package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;
/**
 * @author lihaoran
 * @date 2025/9/19 23:41
 */


/**
 * 计价数据实体类
 */
@Data
public class ApplyDetailChargeVO {

    /**
     * 医嘱ID
     */
    private Integer orderId;

    /**
     * 医嘱名称
     */
    private String orderName;

    /**
     * 计价名称
     */
    private String chargeName;

    /**
     * 计价ID（与护嘱ID对应）
     */
    private Integer chargeId;

    /**
     * 单价
     */
    private Double price;

    /**
     * 数量
     */
    private Double quantity;

    /**
     * 实际单价
     */
    private Double getAmount;

    /**
     * 检查模态编码
     */
    private String modality;
}
