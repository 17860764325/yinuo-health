package org.jeecg.modules.doctor.mapper;

import java.util.List;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 项目详情表
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
public interface CheckProjectDetailMapper extends BaseMapper<CheckProjectDetail> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<CheckProjectDetail>
   */
	public List<CheckProjectDetail> selectByMainId(@Param("mainId") String mainId);
}
