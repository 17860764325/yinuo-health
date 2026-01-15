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
import org.jeecg.modules.doctor.entity.CurRecipeList;
import org.jeecg.modules.doctor.service.ICurRecipeListService;

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
 * @Description: cur_recipe_list
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Api(tags="cur_recipe_list")
@RestController
@RequestMapping("/curRecipeList/curRecipeList")
@Slf4j
public class CurRecipeListController extends JeecgController<CurRecipeList, ICurRecipeListService> {
	@Autowired
	private ICurRecipeListService curRecipeListService;
	
	/**
	 * 分页列表查询
	 *
	 * @param curRecipeList
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "cur_recipe_list-分页列表查询")
	@ApiOperation(value="cur_recipe_list-分页列表查询", notes="cur_recipe_list-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CurRecipeList>> queryPageList(CurRecipeList curRecipeList,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CurRecipeList> queryWrapper = QueryGenerator.initQueryWrapper(curRecipeList, req.getParameterMap());
		queryWrapper.orderByDesc("proc_date");
		queryWrapper.eq("charged","1");
		queryWrapper.gt("proc_date","2024-01-01");
		Page<CurRecipeList> page = new Page<CurRecipeList>(pageNo, pageSize);
		IPage<CurRecipeList> pageList = curRecipeListService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param curRecipeList
	 * @return
	 */
	@AutoLog(value = "cur_recipe_list-添加")
	@ApiOperation(value="cur_recipe_list-添加", notes="cur_recipe_list-添加")
	@RequiresPermissions("curRecipeList:cur_recipe_list:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CurRecipeList curRecipeList) {
		curRecipeListService.save(curRecipeList);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param curRecipeList
	 * @return
	 */
	@AutoLog(value = "cur_recipe_list-编辑")
	@ApiOperation(value="cur_recipe_list-编辑", notes="cur_recipe_list-编辑")
	@RequiresPermissions("curRecipeList:cur_recipe_list:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CurRecipeList curRecipeList) {
		curRecipeListService.updateById(curRecipeList);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "cur_recipe_list-通过id删除")
	@ApiOperation(value="cur_recipe_list-通过id删除", notes="cur_recipe_list-通过id删除")
	@RequiresPermissions("curRecipeList:cur_recipe_list:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		curRecipeListService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "cur_recipe_list-批量删除")
	@ApiOperation(value="cur_recipe_list-批量删除", notes="cur_recipe_list-批量删除")
	@RequiresPermissions("curRecipeList:cur_recipe_list:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.curRecipeListService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "cur_recipe_list-通过id查询")
	@ApiOperation(value="cur_recipe_list-通过id查询", notes="cur_recipe_list-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CurRecipeList> queryById(@RequestParam(name="id",required=true) String id) {
		CurRecipeList curRecipeList = curRecipeListService.getById(id);
		if(curRecipeList==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(curRecipeList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param curRecipeList
    */
    @RequiresPermissions("curRecipeList:cur_recipe_list:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CurRecipeList curRecipeList) {
        return super.exportXls(request, curRecipeList, CurRecipeList.class, "cur_recipe_list");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("curRecipeList:cur_recipe_list:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CurRecipeList.class);
    }

	    /**
     * @description: 中药熬制接口触发
     * @param: ids
     * @return: org.jeecg.common.api.vo.Result
     * @author lhr
     * @date: 10/11/23 11:23 AM
     */
    @AutoLog(value = "中药熬制接口触发")
    @ApiOperation(value = "中药熬制接口触发", notes = "中药熬制接口触发")
    @GetMapping("/boilMedicineBack/{recipeNo}")
    public Result boilMedicineBack(@PathVariable("recipeNo") String recipeNo) {
        return curRecipeListService.boilMedicineBack(recipeNo);
    }

}
