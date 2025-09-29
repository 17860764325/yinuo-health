package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;

import java.util.List;
/**
 * @author lihaoran
 * @date 2025/9/19 23:42
 */


/**
 * 检查项目业务数据实体类
 */
@Data
public class CheckItemData {

    /**
     * 医嘱ID
     */
    private Integer orderId;

    /**
     * 医嘱名称
     */
    private String orderName;

    /**
     * 单价
     */
    private Double orderPrice;

    /**
     * 部位ID（默认与医嘱ID相同）
     */
    private Integer bodypartId;

    /**
     * 部位名称（默认与医嘱名称相同）
     */
    private String bodypartName;

    /**
     * 执行科室ID
     */
    private Integer exeDeptId;

    /**
     * 执行科室名称
     */
    private String exeDeptName;

    /**
     * 备注信息
     */
    private String memo;

    /**
     * 计价数据列表
     */
    private List<ApplyDetailChargeVO> applyDetailChargeVOS;
}
