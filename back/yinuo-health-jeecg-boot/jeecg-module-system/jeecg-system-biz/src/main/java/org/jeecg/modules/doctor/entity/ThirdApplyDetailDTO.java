package org.jeecg.modules.doctor.entity;

import lombok.Data;
import java.util.List;

/**
 * 申请单明细实体类
 * @author 开发者
 * @date 2026-01-25
 */
@Data
public class ThirdApplyDetailDTO {
    /** 检查部位名称（检查类），默认值：头发啊 */
    private String bodypartName = "胸位" ;

    /** 执行科室id（由查询pacs执行科室获取），默认值：87 */
    private Long exeDeptId = 87L;

    /** 执行科室名称（由查询pacs执行科室获取），默认值：放射科 */
    private String exeDeptName = "放射科";

    /** 备注，默认值：好好 */
    private String memo ;

    /** 模态，默认值：CR */
    private String modality = "CR";

    /** 医嘱字典主键ID，默认值：8020206121549432068 */
    private Long orderId = 8020206121549432068L;

    /** 医嘱名称，默认值：胸部正位拍片（胸部） */
    private String orderName = "胸部正位拍片（胸部）";

    /** 费用，默认值：55 */
    private Integer orderPrice = 55;

    /** 部位ID，默认值：8021584380598420738 */
    private Long bodypartId = 8021584380598420738L;

    /** 计价明细列表 */
    private List<ThirdApplyDetailChargeDTO> thirdApplyDetailChargeDtoList;
}