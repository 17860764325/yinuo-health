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
import org.jeecg.modules.doctor.entity.BseDepartment;
import org.jeecg.modules.doctor.service.IBseDepartmentService;

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
 * @Description: bse_department
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Api(tags="bse_department")
@RestController
@RequestMapping("/bseDepartment/bseDepartment")
@Slf4j
public class BseDepartmentController extends JeecgController<BseDepartment, IBseDepartmentService> {
	@Autowired
	private IBseDepartmentService bseDepartmentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param bseDepartment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "bse_department-分页列表查询")
	@ApiOperation(value="bse_department-分页列表查询", notes="bse_department-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<BseDepartment>> queryPageList(BseDepartment bseDepartment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BseDepartment> queryWrapper = QueryGenerator.initQueryWrapper(bseDepartment, req.getParameterMap());
		Page<BseDepartment> page = new Page<BseDepartment>(pageNo, pageSize);
		IPage<BseDepartment> pageList = bseDepartmentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param bseDepartment
	 * @return
	 */
	@AutoLog(value = "bse_department-添加")
	@ApiOperation(value="bse_department-添加", notes="bse_department-添加")
	@RequiresPermissions("bseDepartment:bse_department:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody BseDepartment bseDepartment) {
		bseDepartmentService.save(bseDepartment);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param bseDepartment
	 * @return
	 */
	@AutoLog(value = "bse_department-编辑")
	@ApiOperation(value="bse_department-编辑", notes="bse_department-编辑")
	@RequiresPermissions("bseDepartment:bse_department:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody BseDepartment bseDepartment) {
		bseDepartmentService.updateById(bseDepartment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bse_department-通过id删除")
	@ApiOperation(value="bse_department-通过id删除", notes="bse_department-通过id删除")
	@RequiresPermissions("bseDepartment:bse_department:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		bseDepartmentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bse_department-批量删除")
	@ApiOperation(value="bse_department-批量删除", notes="bse_department-批量删除")
	@RequiresPermissions("bseDepartment:bse_department:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bseDepartmentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "bse_department-通过id查询")
	@ApiOperation(value="bse_department-通过id查询", notes="bse_department-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<BseDepartment> queryById(@RequestParam(name="id",required=true) String id) {
		BseDepartment bseDepartment = bseDepartmentService.getById(id);
		if(bseDepartment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(bseDepartment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param bseDepartment
    */
    @RequiresPermissions("bseDepartment:bse_department:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BseDepartment bseDepartment) {
        return super.exportXls(request, bseDepartment, BseDepartment.class, "bse_department");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("bseDepartment:bse_department:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, BseDepartment.class);
    }

}
