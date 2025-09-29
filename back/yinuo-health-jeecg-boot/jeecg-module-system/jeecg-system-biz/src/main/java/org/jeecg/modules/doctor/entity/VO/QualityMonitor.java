package org.jeecg.modules.doctor.entity.VO;

import lombok.Data;

import java.util.List;
/**
 * @author lihaoran
 * @date 2025/9/19 23:47
 */


/**
 * 质控结果实体类
 */
@Data
public class QualityMonitor {

    /**
     * 类别
     */
    private String category;

    /**
     * 错误信息数组
     */
    private List<String> message;

    /**
     * 质控详情列表
     */
    private List<QualityMonitorDetail> outDetails;
}
