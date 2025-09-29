package org.jeecg.modules.doctor.entity.VO;
import lombok.Data;
/**
 * @author lihaoran
 * @date 2025/9/19 23:47
 */


/**
 * 质控详情实体类
 */
@Data
public class QualityMonitorDetail {

    /**
     * 是否通过
     */
    private String isPass;

    /**
     * 详细信息
     */
    private String outDetailsMsg;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则来源
     */
    private String ruleSource;

    /**
     * 输出类型
     */
    private String outputType;

    /**
     * 输出地址
     */
    private String outputUrl;
}
