package org.jeecg.modules.doctor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.doctor.entity.PeReportDepartmentDetail;
import org.jeecg.modules.doctor.service.IPeReportDepartmentDetailService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 人员检查结果信息表
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Api(tags="人员检查结果信息表")
@RestController
@RequestMapping("/doctor/peReportDepartmentDetail")
@Slf4j
public class PeReportDepartmentDetailController extends JeecgController<PeReportDepartmentDetail, IPeReportDepartmentDetailService> {
	@Autowired
	private IPeReportDepartmentDetailService peReportDepartmentDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param peReportDepartmentDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "人员检查结果信息表-分页列表查询")
	@ApiOperation(value="人员检查结果信息表-分页列表查询", notes="人员检查结果信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PeReportDepartmentDetail>> queryPageList(PeReportDepartmentDetail peReportDepartmentDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PeReportDepartmentDetail> queryWrapper = QueryGenerator.initQueryWrapper(peReportDepartmentDetail, req.getParameterMap());
		Page<PeReportDepartmentDetail> page = new Page<PeReportDepartmentDetail>(pageNo, pageSize);
		IPage<PeReportDepartmentDetail> pageList = peReportDepartmentDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param peReportDepartmentDetail
	 * @return
	 */
	@AutoLog(value = "人员检查结果信息表-添加")
	@ApiOperation(value="人员检查结果信息表-添加", notes="人员检查结果信息表-添加")
	@RequiresPermissions("doctor:pe_report_department_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PeReportDepartmentDetail peReportDepartmentDetail) {
		peReportDepartmentDetailService.save(peReportDepartmentDetail);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param peReportDepartmentDetail
	 * @return
	 */
	@AutoLog(value = "人员检查结果信息表-编辑")
	@ApiOperation(value="人员检查结果信息表-编辑", notes="人员检查结果信息表-编辑")
	@RequiresPermissions("doctor:pe_report_department_detail:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PeReportDepartmentDetail peReportDepartmentDetail) {
		peReportDepartmentDetailService.updateById(peReportDepartmentDetail);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人员检查结果信息表-通过id删除")
	@ApiOperation(value="人员检查结果信息表-通过id删除", notes="人员检查结果信息表-通过id删除")
	@RequiresPermissions("doctor:pe_report_department_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		peReportDepartmentDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "人员检查结果信息表-批量删除")
	@ApiOperation(value="人员检查结果信息表-批量删除", notes="人员检查结果信息表-批量删除")
	@RequiresPermissions("doctor:pe_report_department_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.peReportDepartmentDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "人员检查结果信息表-通过id查询")
	@ApiOperation(value="人员检查结果信息表-通过id查询", notes="人员检查结果信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PeReportDepartmentDetail> queryById(@RequestParam(name="id",required=true) String id) {
		PeReportDepartmentDetail peReportDepartmentDetail = peReportDepartmentDetailService.getById(id);
		if(peReportDepartmentDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(peReportDepartmentDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param peReportDepartmentDetail
    */
    @RequiresPermissions("doctor:pe_report_department_detail:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PeReportDepartmentDetail peReportDepartmentDetail) {
        return super.exportXls(request, peReportDepartmentDetail, PeReportDepartmentDetail.class, "人员检查结果信息表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("doctor:pe_report_department_detail:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PeReportDepartmentDetail.class);
    }

}
