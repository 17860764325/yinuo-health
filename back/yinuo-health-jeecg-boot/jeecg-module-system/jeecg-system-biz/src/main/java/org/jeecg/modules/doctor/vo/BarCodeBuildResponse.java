package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

import java.util.List;

/**
 * @author lihaoran
 * @date 10/18/23 12:23 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "条码生成接收类", description = "条码生成接收类")
public class BarCodeBuildResponse extends BaseResponseEntity {

    private List<BarCodeBuildVo> data;

}
