package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;
/**
 * @author lihaoran
 * @date 2025/9/19 22:25
 */


/**
 * 部位医嘱信息VO
 */
@Data
public class BodyInfoVO {
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
}