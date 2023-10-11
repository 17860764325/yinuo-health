package org.jeecg.modules.doctor.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 项目详情表
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
public interface ICheckProjectDetailService extends IService<CheckProjectDetail> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<CheckProjectDetail>
	 */
	public List<CheckProjectDetail> selectByMainId(String mainId);


}
