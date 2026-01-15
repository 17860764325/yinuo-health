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
import org.jeecg.modules.doctor.entity.AccUsers;
import org.jeecg.modules.doctor.service.IAccUsersService;

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
 * @Description: acc_users
 * @Author: jeecg-boot
 * @Date:   2026-01-15
 * @Version: V1.0
 */
@Api(tags="acc_users")
@RestController
@RequestMapping("/accUsers/accUsers")
@Slf4j
public class AccUsersController extends JeecgController<AccUsers, IAccUsersService> {
	@Autowired
	private IAccUsersService accUsersService;
	
	/**
	 * 分页列表查询
	 *
	 * @param accUsers
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "acc_users-分页列表查询")
	@ApiOperation(value="acc_users-分页列表查询", notes="acc_users-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AccUsers>> queryPageList(AccUsers accUsers,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AccUsers> queryWrapper = QueryGenerator.initQueryWrapper(accUsers, req.getParameterMap());
		Page<AccUsers> page = new Page<AccUsers>(pageNo, pageSize);
		IPage<AccUsers> pageList = accUsersService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param accUsers
	 * @return
	 */
	@AutoLog(value = "acc_users-添加")
	@ApiOperation(value="acc_users-添加", notes="acc_users-添加")
	@RequiresPermissions("accUsers:acc_users:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AccUsers accUsers) {
		accUsersService.save(accUsers);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param accUsers
	 * @return
	 */
	@AutoLog(value = "acc_users-编辑")
	@ApiOperation(value="acc_users-编辑", notes="acc_users-编辑")
	@RequiresPermissions("accUsers:acc_users:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AccUsers accUsers) {
		accUsersService.updateById(accUsers);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "acc_users-通过id删除")
	@ApiOperation(value="acc_users-通过id删除", notes="acc_users-通过id删除")
	@RequiresPermissions("accUsers:acc_users:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		accUsersService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "acc_users-批量删除")
	@ApiOperation(value="acc_users-批量删除", notes="acc_users-批量删除")
	@RequiresPermissions("accUsers:acc_users:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.accUsersService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "acc_users-通过id查询")
	@ApiOperation(value="acc_users-通过id查询", notes="acc_users-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AccUsers> queryById(@RequestParam(name="id",required=true) String id) {
		AccUsers accUsers = accUsersService.getById(id);
		if(accUsers==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(accUsers);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param accUsers
    */
    @RequiresPermissions("accUsers:acc_users:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AccUsers accUsers) {
        return super.exportXls(request, accUsers, AccUsers.class, "acc_users");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("accUsers:acc_users:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AccUsers.class);
    }

}
