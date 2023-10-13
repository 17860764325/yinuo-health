package org.jeecg.modules.doctor.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.doctor.entity.CheckProject;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

import java.util.List;

/**
 * @author lihaoran
 * @date 10/10/23 9:46 PM
 * 查询检验项目的接口实体类
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "查询检验项目的接口实体类", description = "查询检验项目的接口实体类")
public class CheckProjectResponse extends BaseResponseEntity {

    private List<CheckProject> data;

}
