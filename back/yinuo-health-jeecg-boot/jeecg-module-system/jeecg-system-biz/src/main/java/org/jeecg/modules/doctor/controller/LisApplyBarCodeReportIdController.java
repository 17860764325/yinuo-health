package org.jeecg.modules.doctor.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.doctor.entity.ReportDetail;
import org.jeecg.modules.doctor.entity.LisApplyBarCodeReportId;
import org.jeecg.modules.doctor.service.ILisApplyBarCodeReportIdService;
import org.jeecg.modules.doctor.service.IReportDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 条码信息报告信息绑定
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Api(tags="条码信息报告信息绑定")
@RestController
@RequestMapping("/doctor/lisApplyBarCodeReportId")
@Slf4j
public class LisApplyBarCodeReportIdController extends JeecgController<LisApplyBarCodeReportId, ILisApplyBarCodeReportIdService> {

	@Autowired
	private ILisApplyBarCodeReportIdService lisApplyBarCodeReportIdService;

	@Autowired
	private IReportDetailService reportDetailService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param lisApplyBarCodeReportId
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "条码信息报告信息绑定-分页列表查询")
	@ApiOperation(value="条码信息报告信息绑定-分页列表查询", notes="条码信息报告信息绑定-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LisApplyBarCodeReportId>> queryPageList(LisApplyBarCodeReportId lisApplyBarCodeReportId,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<LisApplyBarCodeReportId> queryWrapper = QueryGenerator.initQueryWrapper(lisApplyBarCodeReportId, req.getParameterMap());
		Page<LisApplyBarCodeReportId> page = new Page<LisApplyBarCodeReportId>(pageNo, pageSize);
		IPage<LisApplyBarCodeReportId> pageList = lisApplyBarCodeReportIdService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param lisApplyBarCodeReportId
     * @return
     */
    @AutoLog(value = "条码信息报告信息绑定-添加")
    @ApiOperation(value="条码信息报告信息绑定-添加", notes="条码信息报告信息绑定-添加")
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody LisApplyBarCodeReportId lisApplyBarCodeReportId) {
        lisApplyBarCodeReportIdService.save(lisApplyBarCodeReportId);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param lisApplyBarCodeReportId
     * @return
     */
    @AutoLog(value = "条码信息报告信息绑定-编辑")
    @ApiOperation(value="条码信息报告信息绑定-编辑", notes="条码信息报告信息绑定-编辑")
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody LisApplyBarCodeReportId lisApplyBarCodeReportId) {
        lisApplyBarCodeReportIdService.updateById(lisApplyBarCodeReportId);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "条码信息报告信息绑定-通过id删除")
    @ApiOperation(value="条码信息报告信息绑定-通过id删除", notes="条码信息报告信息绑定-通过id删除")
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        lisApplyBarCodeReportIdService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "条码信息报告信息绑定-批量删除")
    @ApiOperation(value="条码信息报告信息绑定-批量删除", notes="条码信息报告信息绑定-批量删除")
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.lisApplyBarCodeReportIdService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LisApplyBarCodeReportId lisApplyBarCodeReportId) {
        return super.exportXls(request, lisApplyBarCodeReportId, LisApplyBarCodeReportId.class, "条码信息报告信息绑定");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LisApplyBarCodeReportId.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-报告详情-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "报告详情-通过主表ID查询")
	@ApiOperation(value="报告详情-通过主表ID查询", notes="报告详情-通过主表ID查询")
	@GetMapping(value = "/listReportDetailByMainId")
    public Result<IPage<ReportDetail>> listReportDetailByMainId(ReportDetail reportDetail,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ReportDetail> queryWrapper = QueryGenerator.initQueryWrapper(reportDetail, req.getParameterMap());
        Page<ReportDetail> page = new Page<ReportDetail>(pageNo, pageSize);
        IPage<ReportDetail> pageList = reportDetailService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param reportDetail
	 * @return
	 */
	@AutoLog(value = "报告详情-添加")
	@ApiOperation(value="报告详情-添加", notes="报告详情-添加")
	@PostMapping(value = "/addReportDetail")
	public Result<String> addReportDetail(@RequestBody ReportDetail reportDetail) {
		reportDetailService.save(reportDetail);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param reportDetail
	 * @return
	 */
	@AutoLog(value = "报告详情-编辑")
	@ApiOperation(value="报告详情-编辑", notes="报告详情-编辑")
	@RequestMapping(value = "/editReportDetail", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editReportDetail(@RequestBody ReportDetail reportDetail) {
		reportDetailService.updateById(reportDetail);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报告详情-通过id删除")
	@ApiOperation(value="报告详情-通过id删除", notes="报告详情-通过id删除")
	@DeleteMapping(value = "/deleteReportDetail")
	public Result<String> deleteReportDetail(@RequestParam(name="id",required=true) String id) {
		reportDetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "报告详情-批量删除")
	@ApiOperation(value="报告详情-批量删除", notes="报告详情-批量删除")
	@DeleteMapping(value = "/deleteBatchReportDetail")
	public Result<String> deleteBatchReportDetail(@RequestParam(name="ids",required=true) String ids) {
	    this.reportDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportReportDetail")
    public ModelAndView exportReportDetail(HttpServletRequest request, ReportDetail reportDetail) {
		 // Step.1 组装查询条件
		 QueryWrapper<ReportDetail> queryWrapper = QueryGenerator.initQueryWrapper(reportDetail, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ReportDetail> pageList = reportDetailService.list(queryWrapper);
		 List<ReportDetail> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "报告详情");
		 mv.addObject(NormalExcelConstants.CLASS, ReportDetail.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("报告详情报表", "导出人:" + sysUser.getRealname(), "报告详情"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importReportDetail/{mainId}")
    public Result<?> importReportDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
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
				 List<ReportDetail> list = ExcelImportUtil.importExcel(file.getInputStream(), ReportDetail.class, params);
				 for (ReportDetail temp : list) {
                    temp.setReportId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 reportDetailService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
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
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-报告详情-end----------------------------------------------*/




}
