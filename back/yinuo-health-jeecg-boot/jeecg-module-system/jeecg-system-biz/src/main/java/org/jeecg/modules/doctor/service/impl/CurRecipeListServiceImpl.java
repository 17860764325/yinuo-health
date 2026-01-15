package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.*;
import org.jeecg.modules.doctor.mapper.CurRecipeListMapper;
import org.jeecg.modules.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: cur_recipe_list
 * @Author: jeecg-boot
 * @Date: 2026-01-14
 * @Version: V1.0
 */
@Service
public class CurRecipeListServiceImpl extends ServiceImpl<CurRecipeListMapper, CurRecipeList> implements ICurRecipeListService {


    @Autowired
    private ICurUsageSortsService curUsageSortsService;

    @Autowired
    private ICdEmsAddressService cdEmsAddressService;

    @Autowired
    private IDhyPrescriptionService dhyPrescriptionService;

    @Autowired
    private IBseDepartmentService bseDepartmentService;

    @Autowired
    private IAccUsersService accUsersService;

    /**
     * @description: 中药熬药--组装参数
     * @author lhr
     * @date 2026/1/14 20:35
     * @version 1.0
     */
    @Override
    public Result boilMedicineBack(String recipeNo) {
        // 查询出这个处方号下面的所有信息
        List<CurRecipeList> curRecipeLists = this.list(new LambdaQueryWrapper<CurRecipeList>().eq(CurRecipeList::getRecipeNo, recipeNo));
        // 定义参数
        DhyPrescriptionRequest dhyPrescriptionRequest = new DhyPrescriptionRequest();
        List<DhyDrug> dhyDrugs = new ArrayList<>();

        CurRecipeList patient = curRecipeLists.get(0);


        DhyPrescription dhyPrescription = new DhyPrescription();
        dhyPrescription.setDelnum("06080032517085");
        dhyPrescription.setHospitalname("奎文区广文街道社区卫生服务中心");
        dhyPrescription.setHospitalkey("06080032517085");
        dhyPrescription.setPspnum(recipeNo);
        dhyPrescription.setName(patient.getPatientName());
        dhyPrescription.setSex(patient.getSex());
        dhyPrescription.setAge(patient.getAge().toString());
        CurUsageSorts one = curUsageSortsService.getOne(new LambdaQueryWrapper<CurUsageSorts>().eq(CurUsageSorts::getUsageId, patient.getUsageSortId()));
        dhyPrescription.setTakemethod(one.getChineseName());
        dhyPrescription.setDose(patient.getHerbsTimes());
        dhyPrescription.setTakenum(1);
        dhyPrescription.setDrugcount(curRecipeLists.size());
        dhyPrescription.setIsdaijian(patient.getUsage().equals("代配") ? "0" : "1");
        dhyPrescription.setProcesstype(5);// 膏方
        dhyPrescription.setPtype(0);// 门诊
        CdEmsAddress addressPhone = cdEmsAddressService.getOne(new LambdaQueryWrapper<CdEmsAddress>().eq(CdEmsAddress::getPatientNo, patient.getPatientNo()));
        dhyPrescription.setPhone(""); // TODO 手机查询失败，关联关系有问题
        dhyPrescription.setAddress("addressPhone.getContactAddress()");
        BseDepartment department = bseDepartmentService.getOne(new LambdaQueryWrapper<BseDepartment>().eq(BseDepartment::getSerialNo, patient.getDepartmentId()));
        dhyPrescription.setDepartment(department.getDepartmentName()); // 在科室表查询根据department_id
        dhyPrescription.setInpatientarea("");
        dhyPrescription.setWard("");
        dhyPrescription.setSickbed("");
        dhyPrescription.setDiagresult(patient.getDiagnoseName());
        dhyPrescription.setGetdrugtime(DateUtil.format(DateUtil.offsetDay(patient.getChargeDate(), 2), "yyyy-MM-dd HH:mm:ss"));
//        dhyPrescription.setGetdrugnum();
        dhyPrescription.setDecscheme(21);// 煎药方案
        dhyPrescription.setPackagenum(150);
        AccUsers accUsersServiceOne = accUsersService.getOne(new LambdaQueryWrapper<AccUsers>().eq(AccUsers::getSerialNo, patient.getOperatorId()));
        dhyPrescription.setDoperson(accUsersServiceOne.getUserName() );// 在表里找人员名称  patient.getOperatorId()
        dhyPrescription.setDtbcompany("");
        dhyPrescription.setDtbprovince("");
        dhyPrescription.setDtbcity("");
        dhyPrescription.setDtbcounty("");
        dhyPrescription.setDtbaddress("");
        dhyPrescription.setDtbphone("");

        dhyPrescription.setDtbtype(0);
        dhyPrescription.setSoakwater(2000);
        dhyPrescription.setSoaktime(30);
        dhyPrescription.setLabelnum(patient.getHerbsTimes());// 标签数，目前传输的是符数
        dhyPrescription.setRemark("");
        dhyPrescription.setDoctor(accUsersServiceOne.getUserName() );// 在表里找人员名称  patient.getOperatorId()
        dhyPrescription.setFootnote("");
        dhyPrescription.setDecmothed("dhySYSTEM"); //煎药方式 默认：dhySYSTEM
        dhyPrescription.setTakeway("dhySYSTEM"); // 服用方法 默认： dhySYSTEM
        dhyPrescription.setRemarka("");
        dhyPrescription.setRemarkb("");
        dhyPrescription.setPayment("");
        dhyPrescription.setYizhu("");
//        dhyPrescription.setMoney();
        dhyPrescription.setHealthcardno("");
        dhyPrescription.setOutpatientindex("");
//        dhyPrescription.setCasenumber();
//        dhyPrescription.setOutpatientnumber();
//        dhyPrescription.setProcedurejump();
//        dhyPrescription.setPatientfile();
//        dhyPrescription.setFiletype();
//        dhyPrescription.setIsrepetition();
//        dhyPrescription.setParticular();
//        dhyPrescription.setToken("");
//        dhyPrescription.setIsurgent("");
        dhyPrescription.setHospitalpspnum(patient.getRecipeNo());
        dhyPrescription.setPrescribetime(DateUtil.format(patient.getProcDate(), "yyyy-MM-dd HH:mm:ss"));
//        dhyPrescription.setDispensemode();
        dhyPrescription.setMailno("");
        List<DhyPrescription> dhyPrescriptions = new ArrayList<>();
        dhyPrescriptions.add(dhyPrescription);
        dhyPrescriptionRequest.setPrescription(dhyPrescriptions);
        for (CurRecipeList curRecipeList : curRecipeLists) {
            DhyDrug dhyDrug = new DhyDrug();
            dhyDrug.setDrugnum(curRecipeList.getItemNo());
            dhyDrug.setDrugname(curRecipeList.getItemName());
            dhyDrug.setDrugposition(curRecipeList.getDrgSpec());
            dhyDrug.setDrugallnum(curRecipeList.getSingleUseAmount());
            dhyDrug.setTienum(curRecipeList.getHerbsTimes());
            dhyDrug.setDrugweight(curRecipeList.getAmount());
            dhyDrug.setUnit(curRecipeList.getUnit());
            dhyDrug.setDrugdescription(curRecipeList.getRemark());
            dhyDrug.setDescription("");
            dhyDrug.setRetailprice(new BigDecimal(curRecipeList.getPrice()));
            dhyDrug.setBatchnumber("");
            dhyDrug.setMidrugcode("");
            dhyDrugs.add(dhyDrug);
        }
        dhyPrescriptionRequest.setDrug(dhyDrugs);

        PeRegisterList peRegisterList = new PeRegisterList();
        peRegisterList.setPatientNo(patient.getPatientNo());
        peRegisterList.setPatientName(patient.getPatientName());

        Result<DhyPrescriptionResponse> dhyPrescriptionResponseResult = dhyPrescriptionService.dhyPrescriptionService(dhyPrescriptionRequest, peRegisterList);
        // 组装参数 讲借口所需要的接口参数直接存入进去
        return dhyPrescriptionResponseResult;
    }
}
