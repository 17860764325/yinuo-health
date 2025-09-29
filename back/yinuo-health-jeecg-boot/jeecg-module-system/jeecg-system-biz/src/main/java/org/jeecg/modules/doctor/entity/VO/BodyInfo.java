package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;
/**
 * @author lihaoran
 * @date 2025/9/19 23:45
 */


/**
 * 检查部位信息实体类
 */
@Data
public class BodyInfo {

    /**
     * 部位ID
     */
    private Integer bodypartId;

    /**
     * 医嘱ID
     */
    private Integer orderId;

    /**
     * 部位名称
     */
    private String bodypartName;

    /**
     * 医嘱名称
     */
    private String orderName;

    /**
     * 互认编码
     */
    private String mutualCode;

    /**
     * 互认名称
     */
    private String mutualName;

    /**
     * 检查金额
     */
    private Double amount;
}
