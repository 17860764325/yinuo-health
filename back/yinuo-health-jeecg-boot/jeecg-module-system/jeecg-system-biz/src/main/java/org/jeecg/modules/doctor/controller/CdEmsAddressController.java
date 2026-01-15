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
import org.jeecg.modules.doctor.entity.CdEmsAddress;
import org.jeecg.modules.doctor.service.ICdEmsAddressService;

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
 * @Description: cd_ems_address
 * @Author: jeecg-boot
 * @Date:   2026-01-14
 * @Version: V1.0
 */
@Api(tags="cd_ems_address")
@RestController
@RequestMapping("/cdEmsAddress/cdEmsAddress")
@Slf4j
public class CdEmsAddressController extends JeecgController<CdEmsAddress, ICdEmsAddressService> {
	@Autowired
	private ICdEmsAddressService cdEmsAddressService;
	
	/**
	 * 分页列表查询
	 *
	 * @param cdEmsAddress
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "cd_ems_address-分页列表查询")
	@ApiOperation(value="cd_ems_address-分页列表查询", notes="cd_ems_address-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CdEmsAddress>> queryPageList(CdEmsAddress cdEmsAddress,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CdEmsAddress> queryWrapper = QueryGenerator.initQueryWrapper(cdEmsAddress, req.getParameterMap());
		Page<CdEmsAddress> page = new Page<CdEmsAddress>(pageNo, pageSize);
		IPage<CdEmsAddress> pageList = cdEmsAddressService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cdEmsAddress
	 * @return
	 */
	@AutoLog(value = "cd_ems_address-添加")
	@ApiOperation(value="cd_ems_address-添加", notes="cd_ems_address-添加")
	@RequiresPermissions("cdEmsAddress:cd_ems_address:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CdEmsAddress cdEmsAddress) {
		cdEmsAddressService.save(cdEmsAddress);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cdEmsAddress
	 * @return
	 */
	@AutoLog(value = "cd_ems_address-编辑")
	@ApiOperation(value="cd_ems_address-编辑", notes="cd_ems_address-编辑")
	@RequiresPermissions("cdEmsAddress:cd_ems_address:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CdEmsAddress cdEmsAddress) {
		cdEmsAddressService.updateById(cdEmsAddress);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "cd_ems_address-通过id删除")
	@ApiOperation(value="cd_ems_address-通过id删除", notes="cd_ems_address-通过id删除")
	@RequiresPermissions("cdEmsAddress:cd_ems_address:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		cdEmsAddressService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "cd_ems_address-批量删除")
	@ApiOperation(value="cd_ems_address-批量删除", notes="cd_ems_address-批量删除")
	@RequiresPermissions("cdEmsAddress:cd_ems_address:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cdEmsAddressService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "cd_ems_address-通过id查询")
	@ApiOperation(value="cd_ems_address-通过id查询", notes="cd_ems_address-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CdEmsAddress> queryById(@RequestParam(name="id",required=true) String id) {
		CdEmsAddress cdEmsAddress = cdEmsAddressService.getById(id);
		if(cdEmsAddress==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(cdEmsAddress);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cdEmsAddress
    */
    @RequiresPermissions("cdEmsAddress:cd_ems_address:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CdEmsAddress cdEmsAddress) {
        return super.exportXls(request, cdEmsAddress, CdEmsAddress.class, "cd_ems_address");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("cdEmsAddress:cd_ems_address:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CdEmsAddress.class);
    }

}
