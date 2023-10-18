package org.jeecg.modules.doctor.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.doctor.entity.PeRegisterList;
import org.jeecg.modules.doctor.service.IPeRegisterListService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.doctor.vo.LISApplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 人员信息查询
 * @Author: jeecg-boot
 * @Date: 2023-10-09
 * @Version: V1.0
 */
@Api(tags = "人员信息查询")
@RestController
@RequestMapping("/doctor/peRegisterList")
@Slf4j
public class PeRegisterListController extends JeecgController<PeRegisterList, IPeRegisterListService> {
    @Autowired
    private IPeRegisterListService peRegisterListService;

    /**
     * 分页列表查询
     *
     * @param peRegisterList
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "人员信息查询-分页列表查询")
    @ApiOperation(value = "人员信息查询-分页列表查询", notes = "人员信息查询-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<PeRegisterList>> queryPageList(PeRegisterList peRegisterList,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest req) {
        QueryWrapper<PeRegisterList> queryWrapper = QueryGenerator.initQueryWrapper(peRegisterList, req.getParameterMap());
        Page<PeRegisterList> page = new Page<PeRegisterList>(pageNo, pageSize);
        // 添加查询条件
        queryWrapper.notLike("patient_name", "%作废%");
        queryWrapper.eq("status", "3");
        IPage<PeRegisterList> pageList = peRegisterListService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param peRegisterList
     * @return
     */
    @AutoLog(value = "人员信息查询-添加")
    @ApiOperation(value = "人员信息查询-添加", notes = "人员信息查询-添加")
    @RequiresPermissions("doctor:pe_register_list:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody PeRegisterList peRegisterList) {
        peRegisterListService.save(peRegisterList);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param peRegisterList
     * @return
     */
    @AutoLog(value = "人员信息查询-编辑")
    @ApiOperation(value = "人员信息查询-编辑", notes = "人员信息查询-编辑")
    @RequiresPermissions("doctor:pe_register_list:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody PeRegisterList peRegisterList) {
        peRegisterListService.updateById(peRegisterList);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "人员信息查询-通过id删除")
    @ApiOperation(value = "人员信息查询-通过id删除", notes = "人员信息查询-通过id删除")
    @RequiresPermissions("doctor:pe_register_list:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        peRegisterListService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "人员信息查询-批量删除")
    @ApiOperation(value = "人员信息查询-批量删除", notes = "人员信息查询-批量删除")
    @RequiresPermissions("doctor:pe_register_list:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.peRegisterListService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "人员信息查询-通过id查询")
    @ApiOperation(value = "人员信息查询-通过id查询", notes = "人员信息查询-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<PeRegisterList> queryById(@RequestParam(name = "id", required = true) String id) {
        PeRegisterList peRegisterList = peRegisterListService.getById(id);
        if (peRegisterList == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(peRegisterList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param peRegisterList
     */
    @RequiresPermissions("doctor:pe_register_list:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PeRegisterList peRegisterList) {
        return super.exportXls(request, peRegisterList, PeRegisterList.class, "人员信息查询");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("doctor:pe_register_list:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PeRegisterList.class);
    }

    /**
     * @description: 档案查询
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:23 AM
     */
    @AutoLog(value = "档案查询")
    @ApiOperation(value = "档案查询", notes = "档案查询")
    @RequiresPermissions("doctor:pe_register_list:personSearch")
    @GetMapping("/personSearch/{ids}")
    public Result personSearch(@PathVariable("ids") List<String> ids) {
        return peRegisterListService.personSearch(ids);
    }

    /**
     * @description: 创建档案
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:23 AM
     */
    @AutoLog(value = "档案查询")
    @ApiOperation(value = "档案查询", notes = "档案查询")
    @RequiresPermissions("doctor:pe_register_list:personCreate")
    @GetMapping("/personCreate/{ids}")
    public Result personCreate(@PathVariable("ids") List<String> ids) {
        return peRegisterListService.personCreatete(ids);
    }


    /**
     * @description: LIS申请
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:23 AM
     */
    @AutoLog(value = "LIS申请")
    @ApiOperation(value = "LIS申请", notes = "LIS申请")
    @RequiresPermissions("doctor:pe_register_list:LISApply")
    @PostMapping("/LISApply")
    public Result LISApply(@RequestBody LISApplyInfo lisApplyInfo) {
        return peRegisterListService.LISApply(lisApplyInfo);
    }

    /**
     * @description: 条码生成
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:23 AM
     */
    @AutoLog(value = "条码生成")
    @ApiOperation(value = "条码生成", notes = "条码生成")
    @RequiresPermissions("doctor:pe_register_list:barCodeBuild")
    @GetMapping("/barCodeBuild/{ids}")
    public Result barCodeBuild(@PathVariable("ids") List<String> ids) {
        return peRegisterListService.barCodeBuild(ids);
    }

    /**
     * @description: 报告查询维护
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:23 AM
     */
    @AutoLog(value = "报告查询维护")
    @ApiOperation(value = "报告查询维护", notes = "报告查询维护")
    @RequiresPermissions("doctor:pe_register_list:reportSearch")
    @GetMapping("/reportSearch/{ids}")
    public Result reportSearch(@PathVariable("ids") List<String> ids) {
        return peRegisterListService.reportSearch(ids);
    }


    @GetMapping("/newLogTest/{ids}")
    public Result newLogTest(@PathVariable("ids") List<String> ids) {
        return peRegisterListService.newLogTest(ids);
    }

}
