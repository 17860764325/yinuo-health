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
import org.jeecg.modules.doctor.entity.CurAssayMaster;
import org.jeecg.modules.doctor.service.ICurAssayMasterService;

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
 * @Description: 人员检查项目表
 * @Author: jeecg-boot
 * @Date:   2023-10-18
 * @Version: V1.0
 */
@Api(tags="人员检查项目表")
@RestController
@RequestMapping("/doctor/curAssayMaster")
@Slf4j
public class CurAssayMasterController extends JeecgController<CurAssayMaster, ICurAssayMasterService> {
	@Autowired
	private ICurAssayMasterService curAssayMasterService;
	
	/**
	 * 分页列表查询
	 *
	 * @param curAssayMaster
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "人员检查项目表-分页列表查询")
	@ApiOperation(value="人员检查项目表-分页列表查询", notes="人员检查项目表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CurAssayMaster>> queryPageList(CurAssayMaster curAssayMaster,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CurAssayMaster> queryWrapper = QueryGenerator.initQueryWrapper(curAssayMaster, req.getParameterMap());
		Page<CurAssayMaster> page = new Page<CurAssayMaster>(pageNo, pageSize);
		IPage<CurAssayMaster> pageList = curAssayMasterService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param curAssayMaster
	 * @return
	 */
	@AutoLog(value = "人员检查项目表-添加")
	@ApiOperation(value="人员检查项目表-添加", notes="人员检查项目表-添加")
	@RequiresPermissions("doctor:cur_assay_master:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CurAssayMaster curAssayMaster) {
		curAssayMasterService.save(curAssayMaster);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param curAssayMaster
	 * @return
	 */
	@AutoLog(value = "人员检查项目表-编辑")
	@ApiOperation(value="人员检查项目表-编辑", notes="人员检查项目表-编辑")
	@RequiresPermissions("doctor:cur_assay_master:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CurAssayMaster curAssayMaster) {
		curAssayMasterService.updateById(curAssayMaster);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人员检查项目表-通过id删除")
	@ApiOperation(value="人员检查项目表-通过id删除", notes="人员检查项目表-通过id删除")
	@RequiresPermissions("doctor:cur_assay_master:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		curAssayMasterService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "人员检查项目表-批量删除")
	@ApiOperation(value="人员检查项目表-批量删除", notes="人员检查项目表-批量删除")
	@RequiresPermissions("doctor:cur_assay_master:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.curAssayMasterService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "人员检查项目表-通过id查询")
	@ApiOperation(value="人员检查项目表-通过id查询", notes="人员检查项目表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CurAssayMaster> queryById(@RequestParam(name="id",required=true) String id) {
		CurAssayMaster curAssayMaster = curAssayMasterService.getById(id);
		if(curAssayMaster==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(curAssayMaster);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param curAssayMaster
    */
    @RequiresPermissions("doctor:cur_assay_master:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CurAssayMaster curAssayMaster) {
        return super.exportXls(request, curAssayMaster, CurAssayMaster.class, "人员检查项目表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("doctor:cur_assay_master:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CurAssayMaster.class);
    }

}
