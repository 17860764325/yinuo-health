package org.jeecg.modules.doctor.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import org.jeecg.modules.doctor.entity.CheckProject;
import org.jeecg.modules.doctor.vo.CheckProjectPage;
import org.jeecg.modules.doctor.service.ICheckProjectService;
import org.jeecg.modules.doctor.service.ICheckProjectDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 项目表
 * @Author: jeecg-boot
 * @Date: 2023-10-09
 * @Version: V1.0
 */
@Api(tags = "项目表")
@RestController
@RequestMapping("/doctor/checkProject")
@Slf4j
public class CheckProjectController {
    @Autowired
    private ICheckProjectService checkProjectService;
    @Autowired
    private ICheckProjectDetailService checkProjectDetailService;

    /**
     * 分页列表查询
     *
     * @param checkProject
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "项目表-分页列表查询")
    @ApiOperation(value = "项目表-分页列表查询", notes = "项目表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CheckProject>> queryPageList(CheckProject checkProject,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<CheckProject> queryWrapper = QueryGenerator.initQueryWrapper(checkProject, req.getParameterMap());
        Page<CheckProject> page = new Page<CheckProject>(pageNo, pageSize);
        IPage<CheckProject> pageList = checkProjectService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param checkProjectPage
     * @return
     */
    @AutoLog(value = "项目表-添加")
    @ApiOperation(value = "项目表-添加", notes = "项目表-添加")
    @RequiresPermissions("doctor:check_project:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CheckProjectPage checkProjectPage) {
        CheckProject checkProject = new CheckProject();
        BeanUtils.copyProperties(checkProjectPage, checkProject);
        checkProjectService.saveMain(checkProject, checkProjectPage.getCheckProjectDetailList());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param checkProjectPage
     * @return
     */
    @AutoLog(value = "项目表-编辑")
    @ApiOperation(value = "项目表-编辑", notes = "项目表-编辑")
    @RequiresPermissions("doctor:check_project:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CheckProjectPage checkProjectPage) {
        CheckProject checkProject = new CheckProject();
        BeanUtils.copyProperties(checkProjectPage, checkProject);
        CheckProject checkProjectEntity = checkProjectService.getById(checkProject.getId());
        if (checkProjectEntity == null) {
            return Result.error("未找到对应数据");
        }
        checkProjectService.updateMain(checkProject, checkProjectPage.getCheckProjectDetailList());
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "项目表-通过id删除")
    @ApiOperation(value = "项目表-通过id删除", notes = "项目表-通过id删除")
    @RequiresPermissions("doctor:check_project:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        checkProjectService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "项目表-批量删除")
    @ApiOperation(value = "项目表-批量删除", notes = "项目表-批量删除")
    @RequiresPermissions("doctor:check_project:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.checkProjectService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "项目表-通过id查询")
    @ApiOperation(value = "项目表-通过id查询", notes = "项目表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CheckProject> queryById(@RequestParam(name = "id", required = true) String id) {
        CheckProject checkProject = checkProjectService.getById(id);
        if (checkProject == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(checkProject);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "项目详情表-通过主表ID查询")
    @ApiOperation(value = "项目详情表-通过主表ID查询", notes = "项目详情表-通过主表ID查询")
    @GetMapping(value = "/queryCheckProjectDetailByMainId")
    public Result<IPage<CheckProjectDetail>> queryCheckProjectDetailListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<CheckProjectDetail> checkProjectDetailList = checkProjectDetailService.selectByMainId(id);
        IPage<CheckProjectDetail> page = new Page<>();
        page.setRecords(checkProjectDetailList);
        page.setTotal(checkProjectDetailList.size());
        return Result.OK(page);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param checkProject
     */
    @RequiresPermissions("doctor:check_project:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CheckProject checkProject) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<CheckProject> queryWrapper = QueryGenerator.initQueryWrapper(checkProject, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //配置选中数据查询条件
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            queryWrapper.in("id", selectionList);
        }
        //Step.2 获取导出数据
        List<CheckProject> checkProjectList = checkProjectService.list(queryWrapper);

        // Step.3 组装pageList
        List<CheckProjectPage> pageList = new ArrayList<CheckProjectPage>();
        for (CheckProject main : checkProjectList) {
            CheckProjectPage vo = new CheckProjectPage();
            BeanUtils.copyProperties(main, vo);
            List<CheckProjectDetail> checkProjectDetailList = checkProjectDetailService.selectByMainId(main.getId());
            vo.setCheckProjectDetailList(checkProjectDetailList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "项目表列表");
        mv.addObject(NormalExcelConstants.CLASS, CheckProjectPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("项目表数据", "导出人:" + sysUser.getRealname(), "项目表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("doctor:check_project:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<CheckProjectPage> list = ExcelImportUtil.importExcel(file.getInputStream(), CheckProjectPage.class, params);
                for (CheckProjectPage page : list) {
                    CheckProject po = new CheckProject();
                    BeanUtils.copyProperties(page, po);
                    checkProjectService.saveMain(po, page.getCheckProjectDetailList());
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }

    /**
     * @description: 同步所有的 LIS 的检查项目
     * @param:
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/10/23 6:40 PM
     */
    @AutoLog(value = "同步所有的 LIS 的检查项目-LIS申请检验项目查询")
    @ApiOperation(value = "同步所有的 LIS 的检查项目", notes = "同步所有的 LIS 的检查项目")
    @RequiresPermissions("doctor:check_project:async")
    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public Result asyncProject() {
        return checkProjectService.async();
    }

}
