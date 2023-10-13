package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

/**
 * @author lihaoran
 * @date 10/11/23 3:20 PM
 * 档案查询接受接口返回的数据
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "档案查询接受接口返回的数据", description = "档案查询接受接口返回的数据")
public class PersonSearchResponse extends BaseResponseEntity {
    private PersonSearchVo data;
}
