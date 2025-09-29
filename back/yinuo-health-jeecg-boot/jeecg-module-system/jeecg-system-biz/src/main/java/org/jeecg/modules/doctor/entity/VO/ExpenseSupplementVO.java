package org.jeecg.modules.doctor.entity.VO;

/**
 * @author lihaoran
 * @date 2025/9/19 23:46
 */
import lombok.Data;

/**
 * 补录项目明细实体类
 */
@Data
public class ExpenseSupplementVO {

    /**
     * 订单id（门诊体检患者取此字段）
     */
    private String outTradeOrderId;

    /**
     * 住院执行医嘱id（住院患者取此字段）
     */
    private String inExeTradeId;
}
