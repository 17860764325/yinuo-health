package org.jeecg.modules.doctor.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import org.jeecg.modules.doctor.entity.CheckProject;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 项目表
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
public interface ICheckProjectService extends IService<CheckProject> {

	/**
	 * 添加一对多
	 *
	 * @param checkProject
	 * @param checkProjectDetailList
	 */
	public void saveMain(CheckProject checkProject,List<CheckProjectDetail> checkProjectDetailList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param checkProject
	 * @param checkProjectDetailList
	 */
	public void updateMain(CheckProject checkProject,List<CheckProjectDetail> checkProjectDetailList);
	
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

	/**
	 * @description:  同步众阳接口的检验项目的数据
	 * @author lhr
	 * @date 10/10/23 8:28 PM
	 * @version 1.0
	 */
    Result async();
	
}
