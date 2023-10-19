package org.jeecg.modules.doctor.service.impl;

import org.jeecg.modules.doctor.entity.LisApplyBarCodeReportId;
import org.jeecg.modules.doctor.entity.ReportDetail;
import org.jeecg.modules.doctor.mapper.ReportDetailMapper;
import org.jeecg.modules.doctor.mapper.LisApplyBarCodeReportIdMapper;
import org.jeecg.modules.doctor.service.ILisApplyBarCodeReportIdService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 条码信息报告信息绑定
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Service
public class LisApplyBarCodeReportIdServiceImpl extends ServiceImpl<LisApplyBarCodeReportIdMapper, LisApplyBarCodeReportId> implements ILisApplyBarCodeReportIdService {

	@Autowired
	private LisApplyBarCodeReportIdMapper lisApplyBarCodeReportIdMapper;
	@Autowired
	private ReportDetailMapper reportDetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		reportDetailMapper.deleteByMainId(id);
		lisApplyBarCodeReportIdMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			reportDetailMapper.deleteByMainId(id.toString());
			lisApplyBarCodeReportIdMapper.deleteById(id);
		}
	}
	
}
