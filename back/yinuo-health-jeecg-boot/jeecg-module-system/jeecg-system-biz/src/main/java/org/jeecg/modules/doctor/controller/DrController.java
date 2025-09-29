package org.jeecg.modules.doctor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.doctor.service.IDrService;
import org.jeecg.modules.doctor.vo.LISApplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihaoran
 * @date 2025/9/19 22:07
 */
@Api(tags = "DR影像获取")
@RestController("getDrImage")
@RequestMapping("/doctor/dr")
@Slf4j
public class DrController {
    @Autowired
    private IDrService drService;

    // 提交Dr申请

     @AutoLog(value = "提交Dr申请")
    @ApiOperation(value = "提交Dr申请", notes = "提交Dr申请")
    @PostMapping("/drApply")
    public Result DrApply(@RequestBody LISApplyInfo lisApplyInfo) {
        return Result.ok("提交Dr的申请");
    }

    // 查询DR的图片报告

     @AutoLog(value = "查询DR的图片报告")
    @ApiOperation(value = "查询DR的图片报告", notes = "查询DR的图片报告")
    @PostMapping("/drSearch")
    public Result DrSearch(@RequestBody LISApplyInfo lisApplyInfo) {
         return drService.SearchDr(lisApplyInfo);
    }


}
