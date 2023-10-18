package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

import java.util.List;

/**
 * @author lihaoran
 * @date 2023/10/19 00:46
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "报告id查询接收类", description = "报告id查询接收类")
public class ReportIdSearchResponse extends BaseResponseEntity {

    private List<ReportIdSearchVo> data;
}
