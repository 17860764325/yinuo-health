package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

import java.util.List;

/**
 * @author lihaoran
 * @date 2023/10/19 19:06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "报告内容查询", description = "报告内容查询")
public class ReportDetailResponse extends BaseResponseEntity {
    private List<ReportDetailVo> data;
}
