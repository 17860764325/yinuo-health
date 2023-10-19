package org.jeecg.modules.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.doctor.entity.PeReportDepartmentDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 人员检查结果信息表
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
public interface PeReportDepartmentDetailMapper extends BaseMapper<PeReportDepartmentDetail> {

    void add(PeReportDepartmentDetail peReportDepartmentDetail);
    void addUp(PeReportDepartmentDetail peReportDepartmentDetail);
    void addDown(PeReportDepartmentDetail peReportDepartmentDetail);
    void addNoUpDown(PeReportDepartmentDetail peReportDepartmentDetail);

    void edit(PeReportDepartmentDetail peReportDepartmentDetail);
    void editUp(PeReportDepartmentDetail peReportDepartmentDetail);
    void editDown(PeReportDepartmentDetail peReportDepartmentDetail);
    void editNoUpDown(PeReportDepartmentDetail peReportDepartmentDetail);
}
