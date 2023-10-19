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
import org.jeecg.modules.doctor.entity.PeItems;
import org.jeecg.modules.doctor.service.IPeItemsService;

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
 * @Description: 项目详情
 * @Author: jeecg-boot
 * @Date:   2023-10-19
 * @Version: V1.0
 */
@Api(tags="项目详情")
@RestController
@RequestMapping("/doctor/peItems")
@Slf4j
public class PeItemsController extends JeecgController<PeItems, IPeItemsService> {
	@Autowired
	private IPeItemsService peItemsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param peItems
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "项目详情-分页列表查询")
	@ApiOperation(value="项目详情-分页列表查询", notes="项目详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PeItems>> queryPageList(PeItems peItems,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PeItems> queryWrapper = QueryGenerator.initQueryWrapper(peItems, req.getParameterMap());
		Page<PeItems> page = new Page<PeItems>(pageNo, pageSize);
		IPage<PeItems> pageList = peItemsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param peItems
	 * @return
	 */
	@AutoLog(value = "项目详情-添加")
	@ApiOperation(value="项目详情-添加", notes="项目详情-添加")
	@RequiresPermissions("doctor:pe_items:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PeItems peItems) {
		peItemsService.save(peItems);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param peItems
	 * @return
	 */
	@AutoLog(value = "项目详情-编辑")
	@ApiOperation(value="项目详情-编辑", notes="项目详情-编辑")
	@RequiresPermissions("doctor:pe_items:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PeItems peItems) {
		peItemsService.updateById(peItems);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "项目详情-通过id删除")
	@ApiOperation(value="项目详情-通过id删除", notes="项目详情-通过id删除")
	@RequiresPermissions("doctor:pe_items:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		peItemsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "项目详情-批量删除")
	@ApiOperation(value="项目详情-批量删除", notes="项目详情-批量删除")
	@RequiresPermissions("doctor:pe_items:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.peItemsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "项目详情-通过id查询")
	@ApiOperation(value="项目详情-通过id查询", notes="项目详情-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PeItems> queryById(@RequestParam(name="id",required=true) String id) {
		PeItems peItems = peItemsService.getById(id);
		if(peItems==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(peItems);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param peItems
    */
    @RequiresPermissions("doctor:pe_items:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PeItems peItems) {
        return super.exportXls(request, peItems, PeItems.class, "项目详情");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("doctor:pe_items:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PeItems.class);
    }

}
