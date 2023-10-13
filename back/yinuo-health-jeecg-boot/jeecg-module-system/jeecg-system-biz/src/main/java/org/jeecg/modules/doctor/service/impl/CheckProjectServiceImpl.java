package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.CheckProject;
import org.jeecg.modules.doctor.entity.CheckProjectDetail;
import org.jeecg.modules.doctor.mapper.CheckProjectDetailMapper;
import org.jeecg.modules.doctor.mapper.CheckProjectMapper;
import org.jeecg.modules.doctor.service.ICheckProjectDetailService;
import org.jeecg.modules.doctor.service.ICheckProjectService;
import org.jeecg.modules.doctor.util.InterfaceInfo;
import org.jeecg.modules.doctor.util.RequestUtil;
import org.jeecg.modules.doctor.vo.CheckProjectResponse;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

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
    public Result async() {
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
        try {
            // 请求数据
//            String res = RequestUtil.go(InterfaceInfo.CHECK_PROJECTS.getUrl(), InterfaceInfo.CHECK_PROJECTS.getRequestType(), null);
            String res = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"decorate\": true,\n" +
                    "    \"code\": \"0000\",\n" +
                    "    \"message\": \"成功\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"labItemId\": \"1506431\",\n" +
                    "            \"labName\": \"社区查体生化项目\",\n" +
                    "            \"sampleClassId\": \"20000207\",\n" +
                    "            \"sampleClassName\": \"血清\",\n" +
                    "            \"reportItemId\": \"20000384\",\n" +
                    "            \"orderId\": \"1506432\",\n" +
                    "            \"orderName\": \"社区查体生化项目\",\n" +
                    "            \"itemList\": [\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000126\",\n" +
                    "                    \"itemNo\": \"ALB\",\n" +
                    "                    \"itemName\": \"白蛋白\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000130\",\n" +
                    "                    \"itemNo\": \"ALT\",\n" +
                    "                    \"itemName\": \"丙氨酸氨基转移酶\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000156\",\n" +
                    "                    \"itemNo\": \"AST\",\n" +
                    "                    \"itemName\": \"天门冬氨酸氨基转移酶\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000283\",\n" +
                    "                    \"itemNo\": \"Cr\",\n" +
                    "                    \"itemName\": \"肌酐\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000299\",\n" +
                    "                    \"itemNo\": \"DBIL\",\n" +
                    "                    \"itemName\": \"直接胆红素\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000397\",\n" +
                    "                    \"itemNo\": \"HDL-C\",\n" +
                    "                    \"itemName\": \"高密度脂蛋白胆固醇\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000536\",\n" +
                    "                    \"itemNo\": \"LDL-C\",\n" +
                    "                    \"itemName\": \"低密度脂蛋白胆固醇\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000753\",\n" +
                    "                    \"itemNo\": \"TBIL\",\n" +
                    "                    \"itemName\": \"总胆红素\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000755\",\n" +
                    "                    \"itemNo\": \"TCH\",\n" +
                    "                    \"itemName\": \"总胆固醇\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000761\",\n" +
                    "                    \"itemNo\": \"TG\",\n" +
                    "                    \"itemName\": \"甘油三脂\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000774\",\n" +
                    "                    \"itemNo\": \"TP\",\n" +
                    "                    \"itemName\": \"总蛋白\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000794\",\n" +
                    "                    \"itemNo\": \"UA\",\n" +
                    "                    \"itemName\": \"尿酸\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000798\",\n" +
                    "                    \"itemNo\": \"Urea\",\n" +
                    "                    \"itemName\": \"尿素\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1506431\",\n" +
                    "                    \"itemId\": \"20000913\",\n" +
                    "                    \"itemNo\": \"γ-GT\",\n" +
                    "                    \"itemName\": \"γ-谷氨酰基转移酶\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"labItemId\": \"78016\",\n" +
                    "            \"labName\": \"葡萄糖测定(化学法)\",\n" +
                    "            \"sampleClassId\": \"20000207\",\n" +
                    "            \"sampleClassName\": \"血清\",\n" +
                    "            \"reportItemId\": \"20000034\",\n" +
                    "            \"orderId\": \"78016\",\n" +
                    "            \"orderName\": \"葡萄糖测定(化学法)\",\n" +
                    "            \"itemList\": [\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"78016\",\n" +
                    "                    \"itemId\": \"20000171\",\n" +
                    "                    \"itemNo\": \"BS\",\n" +
                    "                    \"itemName\": \"葡萄糖\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"labItemId\": \"1721009\",\n" +
                    "            \"labName\": \"尿液分析\",\n" +
                    "            \"sampleClassId\": \"20000113\",\n" +
                    "            \"sampleClassName\": \"尿液\",\n" +
                    "            \"reportItemId\": \"6055430672417030146\",\n" +
                    "            \"orderId\": \"6055430655571132422\",\n" +
                    "            \"orderName\": \"尿液分析\",\n" +
                    "            \"itemList\": [\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000167\",\n" +
                    "                    \"itemNo\": \"BIL\",\n" +
                    "                    \"itemName\": \"胆红素\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000168\",\n" +
                    "                    \"itemNo\": \"BLD\",\n" +
                    "                    \"itemName\": \"尿潜血\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000203\",\n" +
                    "                    \"itemNo\": \"cbxb\",\n" +
                    "                    \"itemName\": \"镜检白细胞\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000255\",\n" +
                    "                    \"itemNo\": \"chxb\",\n" +
                    "                    \"itemName\": \"镜检红细胞\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000364\",\n" +
                    "                    \"itemNo\": \"GLU（U）\",\n" +
                    "                    \"itemName\": \"葡萄糖\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000368\",\n" +
                    "                    \"itemNo\": \"gx\",\n" +
                    "                    \"itemName\": \"管型\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000510\",\n" +
                    "                    \"itemNo\": \"KET\",\n" +
                    "                    \"itemName\": \"酮体\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000537\",\n" +
                    "                    \"itemNo\": \"LEU\",\n" +
                    "                    \"itemName\": \"白细胞\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000598\",\n" +
                    "                    \"itemNo\": \"NIT\",\n" +
                    "                    \"itemName\": \"亚硝酸盐\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000637\",\n" +
                    "                    \"itemNo\": \"PH\",\n" +
                    "                    \"itemName\": \"酸碱度\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000657\",\n" +
                    "                    \"itemNo\": \"PRO\",\n" +
                    "                    \"itemName\": \"蛋白质\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000717\",\n" +
                    "                    \"itemNo\": \"SG\",\n" +
                    "                    \"itemName\": \"比重\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000799\",\n" +
                    "                    \"itemNo\": \"URO\",\n" +
                    "                    \"itemName\": \"尿胆原\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20000803\",\n" +
                    "                    \"itemNo\": \"VC\",\n" +
                    "                    \"itemName\": \"维生素C\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"1721009\",\n" +
                    "                    \"itemId\": \"20001036\",\n" +
                    "                    \"itemNo\": \"尿液上皮细胞\",\n" +
                    "                    \"itemName\": \"上皮细胞\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"labItemId\": \"77834\",\n" +
                    "            \"labName\": \"全血细胞计数+五分类\",\n" +
                    "            \"sampleClassId\": \"20000213\",\n" +
                    "            \"sampleClassName\": \"血液\",\n" +
                    "            \"reportItemId\": \"20000011\",\n" +
                    "            \"orderId\": \"77834\",\n" +
                    "            \"orderName\": \"血常规\",\n" +
                    "            \"itemList\": [\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000160\",\n" +
                    "                    \"itemNo\": \"BAS#\",\n" +
                    "                    \"itemName\": \"嗜碱性粒细胞计数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000161\",\n" +
                    "                    \"itemNo\": \"BAS%\",\n" +
                    "                    \"itemName\": \"嗜碱性粒细胞百分数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000319\",\n" +
                    "                    \"itemNo\": \"EOS#\",\n" +
                    "                    \"itemName\": \"嗜酸性粒细胞计数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000320\",\n" +
                    "                    \"itemNo\": \"EOS%\",\n" +
                    "                    \"itemName\": \"嗜酸性粒细胞百分数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000393\",\n" +
                    "                    \"itemNo\": \"HCT\",\n" +
                    "                    \"itemName\": \"红细胞压积\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000401\",\n" +
                    "                    \"itemNo\": \"HGB\",\n" +
                    "                    \"itemName\": \"血红蛋白浓度\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000460\",\n" +
                    "                    \"itemNo\": \"IG#\",\n" +
                    "                    \"itemName\": \"幼稚粒细胞绝对值\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000461\",\n" +
                    "                    \"itemNo\": \"IG%\",\n" +
                    "                    \"itemName\": \"幼稚粒细胞百分比\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000556\",\n" +
                    "                    \"itemNo\": \"LYM#\",\n" +
                    "                    \"itemName\": \"淋巴细胞计数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000557\",\n" +
                    "                    \"itemNo\": \"LYM%\",\n" +
                    "                    \"itemName\": \"淋巴细胞百分数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000561\",\n" +
                    "                    \"itemNo\": \"MCH\",\n" +
                    "                    \"itemName\": \"平均血红蛋白含量\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000562\",\n" +
                    "                    \"itemNo\": \"MCHC\",\n" +
                    "                    \"itemName\": \"平均血红蛋白浓度\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000563\",\n" +
                    "                    \"itemNo\": \"MCV\",\n" +
                    "                    \"itemName\": \"红细胞平均体积\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000578\",\n" +
                    "                    \"itemNo\": \"MON#\",\n" +
                    "                    \"itemName\": \"单核细胞计数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000579\",\n" +
                    "                    \"itemNo\": \"MON%\",\n" +
                    "                    \"itemName\": \"单核细胞百分数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000584\",\n" +
                    "                    \"itemNo\": \"MPV\",\n" +
                    "                    \"itemName\": \"血小板平均体积\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000594\",\n" +
                    "                    \"itemNo\": \"NEU#\",\n" +
                    "                    \"itemName\": \"中性粒细胞计数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000595\",\n" +
                    "                    \"itemNo\": \"NEU%\",\n" +
                    "                    \"itemName\": \"中性粒细胞百分数\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000632\",\n" +
                    "                    \"itemNo\": \"PCT\",\n" +
                    "                    \"itemName\": \"血小板压积\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000635\",\n" +
                    "                    \"itemNo\": \"PDW\",\n" +
                    "                    \"itemName\": \"血小板体积分布宽度\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000644\",\n" +
                    "                    \"itemNo\": \"P-LCR\",\n" +
                    "                    \"itemName\": \"大型血小板比率\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000645\",\n" +
                    "                    \"itemNo\": \"PLT\",\n" +
                    "                    \"itemName\": \"血小板\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000687\",\n" +
                    "                    \"itemNo\": \"RBC\",\n" +
                    "                    \"itemName\": \"红细胞\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000690\",\n" +
                    "                    \"itemNo\": \"RDW-CV\",\n" +
                    "                    \"itemName\": \"红细胞分布幅\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000691\",\n" +
                    "                    \"itemNo\": \"RDW-SD\",\n" +
                    "                    \"itemName\": \"红细胞分布幅\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"labItemId\": \"77834\",\n" +
                    "                    \"itemId\": \"20000807\",\n" +
                    "                    \"itemNo\": \"WBC\",\n" +
                    "                    \"itemName\": \"白细胞计数\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"traceId\": \"9ad9763a-c0f4-4f6c-a913-19d31c0d6b04\",\n" +
                    "    \"exceptionName\": null,\n" +
                    "    \"qualityMonitor\": null,\n" +
                    "    \"ignoreQualityMonitor\": false,\n" +
                    "    \"level\": \"info\",\n" +
                    "    \"service\": \"msun-middle-business-lis\",\n" +
                    "    \"businessException\": false\n" +
                    "}";
            // 判断是否 json 串
            if (JSONUtil.isJson(res)) {
                // 将 json 字符串 解析为 实体类
                response = JSONUtil.toBean(res, CheckProjectResponse.class);
                if (!response.getSuccess()){
                    return Result.error("接口请求失败！" + response.getMessage());
                }
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        // 判断返回信息是否为空
        if (BeanUtil.isEmpty(response)||CollUtil.isEmpty(response.getData())) {
            return Result.error("接口返回数据为空，维护失败！");
        }
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

        return  Result.OK("同步成功！");
    }

}
