package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.CheckProject;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import org.jeecg.modules.doctor.entity.PeItems;
import org.jeecg.modules.doctor.mapper.CheckProjectDetailMapper;
import org.jeecg.modules.doctor.mapper.CheckProjectMapper;
import org.jeecg.modules.doctor.service.ICheckProjectDetailService;
import org.jeecg.modules.doctor.service.ICheckProjectService;
import org.jeecg.modules.doctor.service.IPeItemsService;
import org.jeecg.modules.doctor.util.InterfaceInfo;
import org.jeecg.modules.doctor.util.RequestUtil;
import org.jeecg.modules.doctor.vo.CheckProjectResponse;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 项目表
 * @Author: jeecg-boot
 * @Date: 2023-10-09
 * @Version: V1.0
 */
@Service
public class CheckProjectServiceImpl extends ServiceImpl<CheckProjectMapper, CheckProject> implements ICheckProjectService {

    @Autowired
    private CheckProjectMapper checkProjectMapper;
    @Autowired
    private CheckProjectDetailMapper checkProjectDetailMapper;

    @Autowired
    private ICheckProjectDetailService checkProjectDetailService;

    @Autowired
    private IPeItemsService peItemsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMain(CheckProject checkProject, List<CheckProjectDetail> checkProjectDetailList) {
        checkProjectMapper.insert(checkProject);
        if (checkProjectDetailList != null && checkProjectDetailList.size() > 0) {
            for (CheckProjectDetail entity : checkProjectDetailList) {
                //外键设置
                entity.setCheckProjectId(checkProject.getLabItemId());
                checkProjectDetailMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMain(CheckProject checkProject, List<CheckProjectDetail> checkProjectDetailList) {
        checkProjectMapper.updateById(checkProject);

        //1.先删除子表数据
        checkProjectDetailMapper.deleteByMainId(checkProject.getLabItemId());

        //2.子表数据重新插入
        if (checkProjectDetailList != null && checkProjectDetailList.size() > 0) {
            for (CheckProjectDetail entity : checkProjectDetailList) {
                //外键设置
                entity.setCheckProjectId(checkProject.getLabItemId());
                checkProjectDetailMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMain(String id) {
        checkProjectDetailMapper.deleteByMainId(id);
        checkProjectMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            checkProjectDetailMapper.deleteByMainId(id.toString());
            checkProjectMapper.deleteById(id);
        }
    }

    /**
     * @description: 同步项目数据接口
     * @author lhr
     * @date 10/10/23 8:31 PM
     * @version 1.0
     */
    @Override
    @Transactional
    public Result async(String labName) {
		/* 先查询数据库中已经维护数据的项目数据，将项目更新后，需要保留对应关系。
		 还是id 对应关系保存一下，然后在请求借口再进行存储对应关系*/

        // 查询数据库中的所有数据--主表数据
        List<CheckProject> list = list(new LambdaQueryWrapper<CheckProject>());

        // 查询所有的子表数据
        List<CheckProjectDetail> listDetail = checkProjectDetailService.list(new LambdaQueryWrapper<CheckProjectDetail>());

        // 绑定关系的主表数据
        List<CheckProject> bindingList = new ArrayList<>();
        // 如果查询的主表数据不为空就筛选，有绑定关系的主表数据
        if (CollUtil.isNotEmpty(list)) {
            // 进行循环筛选
            for (CheckProject checkProject : list) {
                // 筛选 mineProjectNo 字段不为空的数据进行保留
                if (StrUtil.isNotEmpty(checkProject.getMineProjectNo())) {
                    // 存储起来
                    bindingList.add(checkProject);
                }
            }
        }

        // 有绑定关系的字表
        List<CheckProjectDetail> bindingListDetail = new ArrayList<>();
        // 如果查询的所有的详细的项目为空，那就不进行此操作
        if (CollUtil.isNotEmpty(listDetail)) {
            // 进行循环筛选
            for (CheckProjectDetail checkProjectDetail : listDetail) {
                // 如果字表 mineProjectNo  字段 不为空的话就是有绑定关系
                if (StrUtil.isNotEmpty(checkProjectDetail.getMineProjectNo())) {
                    bindingListDetail.add(checkProjectDetail);
                }
            }
        }

        CheckProjectResponse response = null;
        // 请求接口 ，将众阳的新的项目数据请求过来
          // 指定查询：糖化血红蛋白【色谱法】
        Map<String, Object> params = new HashMap<>();
        params.put("labName", labName);
          String res = "";
        try {
            // 请求数据
            if (StrUtil.isNotEmpty(labName)) {
                res = RequestUtil.go(InterfaceInfo.CHECK_PROJECTS.getUrl(), InterfaceInfo.CHECK_PROJECTS.getRequestType(), params);
            } else {
                res = RequestUtil.go(InterfaceInfo.CHECK_PROJECTS.getUrl(), InterfaceInfo.CHECK_PROJECTS.getRequestType(), null);
            }
            // 判断是否 json 串
            if (JSONUtil.isJson(res)) {
                // 将 json 字符串 解析为 实体类
                response = JSONUtil.toBean(res, CheckProjectResponse.class);
                if (!response.getSuccess()) {
                    return Result.error("接口请求失败！" + response.getMessage());
                }
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        // 判断返回信息是否为空
        if (BeanUtil.isEmpty(response) || CollUtil.isEmpty(response.getData())) {
            return Result.error("接口返回数据为空，维护失败！");
        }
        // pe_items 对应详情表用到的所有的数据
        List<PeItems> peItems = peItemsService.list();
        // 请求返回的主表数据
        List<CheckProject> responseCheckProjectList = new ArrayList<>();
        // 请求返回的字表数据
        List<CheckProjectDetail> responseCheckProjectDetailList = new ArrayList<>();
        // 处理接受回来的数据，先讲查询到的数据，分别存储在 请求主表 list 和 详情表 list
        for (CheckProject datum : response.getData()) {
            // 判断主表项目是否为空
            if (BeanUtil.isNotEmpty(datum)) {
                // 不为空添加到返回主表数据 list 中
                responseCheckProjectList.add(datum);
                // 判断详情数据表是否为空
                if (CollUtil.isNotEmpty(datum.getItemList())) {
                    // 循环主表中的详情数据，存储在详情数据表中
                    for (CheckProjectDetail checkProjectDetail : datum.getItemList()) {
                        // 判断详情表是否为空
                        if (BeanUtil.isNotEmpty(checkProjectDetail)) {
                            // 存放到详情数据表
                            // 根据返回的名项目编码查询对应的详细数据
                            // 维护详情对应到的数据
                            List<PeItems> collect = peItems.stream().filter(item -> item.getInterfaceNo().contains(checkProjectDetail.getItemNo())).collect(Collectors.toList());
                            if (CollUtil.isNotEmpty(collect)) {
                                checkProjectDetail.setMineProjectNo(collect.get(0).getItemNo());
                            }
                            checkProjectDetail.setCheckProjectId(datum.getLabItemId());
                            responseCheckProjectDetailList.add(checkProjectDetail);
                        }
                    }
                }
            }
        }
        // 将之前维护的对应关系，重新维护到新的新同步的数据上

        // 判断绑定关系表是否为空
        if (CollUtil.isNotEmpty(bindingList)) {
            // 主表对应关系为：labItemId 和 系统内的项目编码 mineProjectNo
            for (CheckProject checkProject : bindingList) {
                for (CheckProject responseProject : responseCheckProjectList) {
                    // 如果绑定关系数据的项目ID 等同于，返回数据的 项目ID 则将绑定关系数据的 维护新的数据上
                    // TODO 这样可能出现一种情况，第一次维护了同id的一条数据，但有可能通过这次数据同步，将所有id相通的数据都维护上对应关系
                    // TODO 主要问题，怎么确定项目的唯一性
                    if (checkProject.getLabItemId().equals(responseProject.getLabItemId())) {
                        // 维护对应的系统项目编码
                        responseProject.setMineProjectNo(checkProject.getMineProjectNo());
                        // 维护预留字段
                        responseProject.setMineItemRemork(checkProject.getMineItemRemork());
                    }
                }
            }
        }

        // 判断子表对应关系表是否为空
        if (CollUtil.isNotEmpty(bindingListDetail)) {
            // 子表对应关系为：itemId 和 系统内的项目编码 mineProjectNo
            for (CheckProjectDetail checkProjectDetail : bindingListDetail) {
                for (CheckProjectDetail responseProjectDetail : responseCheckProjectDetailList) {
                    if (checkProjectDetail.getItemId().equals(responseProjectDetail.getItemId())){
                        // 维护自己系统编码
                        responseProjectDetail.setMineProjectNo(checkProjectDetail.getMineProjectNo());
                        // 维护自己系统的预留字段
                        responseProjectDetail.setMineProjectRemock(checkProjectDetail.getMineProjectRemock());
                    }
                }
            }
        }

        // 处理完对应关系，将数据库中原先的所有数据删除，插入新同步过来的数据（全删全建）
        // 主表
        // 删除
        this.remove(new LambdaQueryWrapper<CheckProject>().isNotNull(CheckProject::getId));
        // 创建
        this.saveBatch(responseCheckProjectList);

        // 子表
        // 删除
        checkProjectDetailService.remove(new LambdaQueryWrapper<CheckProjectDetail>().isNotNull(CheckProjectDetail::getId));
        // 创建
        checkProjectDetailService.saveBatch(responseCheckProjectDetailList);

        return Result.OK("同步成功！");
    }

}
