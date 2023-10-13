package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

/**
 * @author lihaoran
 * @date 10/13/23 12:14 AM
 * 创建档案接受类
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "创建档案接受类", description = "创建档案接受类")
public class PersonCreateResponse extends BaseResponseEntity {

    private PersonCreateVo data;

}
