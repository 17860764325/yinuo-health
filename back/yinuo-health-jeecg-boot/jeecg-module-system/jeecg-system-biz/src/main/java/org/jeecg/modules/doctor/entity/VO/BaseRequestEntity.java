package org.jeecg.modules.doctor.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author lihaoran
 * @date 10/9/23 8:23 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "接口返回信息接受类", description = "接口返回信息接受类")
public class  BaseRequestEntity {
    // 状态码
    @TableField(exist = false)
    private String code;
    // 是否成功
    @TableField(exist = false)
    private boolean success;
    // 返回信息
    @TableField(exist = false)
    private String message;
    // 跟踪ID
    @TableField(exist = false)
    private String traceId;

    @TableField(exist = false)
    private String qualityMonitor;

    @TableField(exist = false)
    private String ignoreQualityMonitor;
    // 级别
    @TableField(exist = false)
    private String level;

    @TableField(exist = false)
    private String service;
    // 业务是否异常
    @TableField(exist = false)
    private String businessException;
    // 异常名称
    @TableField(exist = false)
    private String exceptionName;

}
