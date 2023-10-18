package org.jeecg.modules.doctor.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.doctor.vo.LISApplyInfo;

import java.util.List;

/**
 * @Description: 人员信息查询
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
public interface IPeRegisterListService extends IService<PeRegisterList> {

    /** 
     * @description: 人员档案查询 
     * @param: ids 
     * @return: org.jeecg.common.api.vo.Result 
     * @author lhr
     * @date: 10/11/23 3:24 PM
     */ 
    Result personSearch(List<String> ids);

    Result personCreatete(List<String> ids);

    Result newLogTest(List<String> ids);

    Result LISApply(LISApplyInfo lisApplyInfo);

    Result barCodeBuild(List<String> ids);
}
