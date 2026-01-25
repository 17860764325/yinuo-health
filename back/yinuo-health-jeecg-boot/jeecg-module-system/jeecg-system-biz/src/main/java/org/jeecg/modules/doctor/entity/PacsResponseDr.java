package org.jeecg.modules.doctor.entity;

import lombok.Data;
import org.jeecg.modules.doctor.entity.VO.BaseResponseEntity;

import java.util.List;

/**
 *
 *
 * @author lihaoran
 * @date 2026/1/25 12:25
 */
@Data
public class PacsResponseDr extends BaseResponseEntity {

    private List<PacsReportDetailDTO> data;

}
