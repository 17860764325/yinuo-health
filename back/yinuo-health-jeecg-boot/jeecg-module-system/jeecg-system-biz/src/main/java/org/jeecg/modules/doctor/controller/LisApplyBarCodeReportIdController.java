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
import org.jeecg.modules.doctor.entity.LisApplyBarCodeReportId;
import org.jeecg.modules.doctor.service.ILisApplyBarCodeReportIdService;

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
 * @Description: 条码信息报告信息绑定
 * @Author: jeecg-boot
 * @Date:   2023-10-18
 * @Version: V1.0
 */
@Api(tags="条码信息报告信息绑定")
@RestController
@RequestMapping("/doctor/lisApplyBarCodeReportId")
@Slf4j
public class LisApplyBarCodeReportIdController extends JeecgController<LisApplyBarCodeReportId, ILisApplyBarCodeReportIdService> {
	@Autowired
	private ILisApplyBarCodeReportIdService lisApplyBarCodeReportIdService;
	
	/**
	 * 分页列表查询
	 *
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
	 *
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
	 *
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
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "条码信息报告信息绑定-通过id删除")
	@ApiOperation(value="条码信息报告信息绑定-通过id删除", notes="条码信息报告信息绑定-通过id删除")
	@RequiresPermissions("doctor:lis_apply_bar_code_report_id:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lisApplyBarCodeReportIdService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "条码信息报告信息绑定-批量删除")
	@ApiOperation(value="条码信息报告信息绑定-批量删除", notes="条码信息报告信息绑定-批量删除")
	@RequiresPermissions("doctor:lis_apply_bar_code_report_id:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lisApplyBarCodeReportIdService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "条码信息报告信息绑定-通过id查询")
	@ApiOperation(value="条码信息报告信息绑定-通过id查询", notes="条码信息报告信息绑定-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LisApplyBarCodeReportId> queryById(@RequestParam(name="id",required=true) String id) {
		LisApplyBarCodeReportId lisApplyBarCodeReportId = lisApplyBarCodeReportIdService.getById(id);
		if(lisApplyBarCodeReportId==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lisApplyBarCodeReportId);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lisApplyBarCodeReportId
    */
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LisApplyBarCodeReportId lisApplyBarCodeReportId) {
        return super.exportXls(request, lisApplyBarCodeReportId, LisApplyBarCodeReportId.class, "条码信息报告信息绑定");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("doctor:lis_apply_bar_code_report_id:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LisApplyBarCodeReportId.class);
    }

}
