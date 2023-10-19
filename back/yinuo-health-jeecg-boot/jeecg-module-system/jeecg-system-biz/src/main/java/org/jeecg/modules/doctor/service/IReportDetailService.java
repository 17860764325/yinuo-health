package org.jeecg.modules.doctor.service;

import org.jeecg.modules.doctor.entity.ReportDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 报告详情
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
public interface IReportDetailService extends IService<ReportDetail> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<ReportDetail>
   */
	public List<ReportDetail> selectByMainId(String mainId);
}
