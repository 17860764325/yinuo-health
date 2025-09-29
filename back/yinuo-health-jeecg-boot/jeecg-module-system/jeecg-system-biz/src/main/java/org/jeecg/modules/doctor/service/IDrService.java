package org.jeecg.modules.doctor.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.vo.LISApplyInfo;

/**
 * @author lihaoran
 * @date 2025/9/19 22:21
 */
public interface IDrService {
     Result SearchDr(LISApplyInfo lisApplyInfo);
}
