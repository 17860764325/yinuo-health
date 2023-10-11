package org.jeecg.modules.doctor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import org.jeecg.modules.doctor.mapper.CheckProjectDetailMapper;
import org.jeecg.modules.doctor.service.ICheckProjectDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 项目详情表
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
@Service
public class CheckProjectDetailServiceImpl extends ServiceImpl<CheckProjectDetailMapper, CheckProjectDetail> implements ICheckProjectDetailService {
	
	@Autowired
	private CheckProjectDetailMapper checkProjectDetailMapper;
	
	@Override
	public List<CheckProjectDetail> selectByMainId(String mainId) {
		return checkProjectDetailMapper.selectByMainId(mainId);
	}


}
