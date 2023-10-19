package org.jeecg.modules.doctor.mapper;

import java.util.List;
import org.jeecg.modules.doctor.entity.ReportDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 报告详情
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
public interface ReportDetailMapper extends BaseMapper<ReportDetail> {

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
    * @return List<ReportDetail>
    */
	public List<ReportDetail> selectByMainId(@Param("mainId") String mainId);

}
