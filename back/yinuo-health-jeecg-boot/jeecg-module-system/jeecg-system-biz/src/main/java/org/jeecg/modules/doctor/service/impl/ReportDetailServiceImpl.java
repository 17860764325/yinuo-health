package org.jeecg.modules.doctor.service.impl;

import org.jeecg.modules.doctor.entity.ReportDetail;
import org.jeecg.modules.doctor.mapper.ReportDetailMapper;
import org.jeecg.modules.doctor.service.IReportDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 报告详情
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Service
public class ReportDetailServiceImpl extends ServiceImpl<ReportDetailMapper, ReportDetail> implements IReportDetailService {
	
	@Autowired
	private ReportDetailMapper reportDetailMapper;
	
	@Override
	public List<ReportDetail> selectByMainId(String mainId) {
		return reportDetailMapper.selectByMainId(mainId);
	}
}
