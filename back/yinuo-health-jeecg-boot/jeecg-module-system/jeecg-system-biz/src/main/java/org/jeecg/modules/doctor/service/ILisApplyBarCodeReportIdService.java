package org.jeecg.modules.doctor.service;

import org.jeecg.modules.doctor.entity.ReportDetail;
import org.jeecg.modules.doctor.entity.LisApplyBarCodeReportId;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 条码信息报告信息绑定
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
public interface ILisApplyBarCodeReportIdService extends IService<LisApplyBarCodeReportId> {

	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
