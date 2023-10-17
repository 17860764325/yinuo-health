package org.jeecg.modules.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.doctor.entity.PatItems;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 人员信息查询
 * @Author: jeecg-boot
 * @Date:   2023-10-09
 * @Version: V1.0
 */
public interface PeRegisterListMapper extends BaseMapper<PeRegisterList> {

    /**
     * @description: 查询患者检查的项目--本地数据库
     * @param: patientNo
     * @return: org.jeecg.modules.doctor.entity.PatItems
     * @author lhr
     * @date: 10/16/23 11:54 PM
     */
    List<PatItems> getPatItems(String patientNo);
}
