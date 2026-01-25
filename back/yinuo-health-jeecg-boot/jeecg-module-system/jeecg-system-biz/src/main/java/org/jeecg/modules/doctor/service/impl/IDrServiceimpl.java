package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.demo.peReportDepartmentImages.entity.PeReportDepartmentImages;
import org.jeecg.modules.demo.peReportDepartmentImages.service.IPeReportDepartmentImagesService;
import org.jeecg.modules.doctor.entity.*;
//import org.jeecg.modules.doctor.entity.PeReportDepartmentImages;
//import org.jeecg.modules.doctor.service.IPeReportDepartmentImagesService;
import org.jeecg.modules.doctor.entity.VO.PatientCheckVO;
import org.jeecg.modules.doctor.service.IDrService;
import org.jeecg.modules.doctor.service.IPeRegisterListService;
import org.jeecg.modules.doctor.service.IPeReportDepartmentDetailService;
import org.jeecg.modules.doctor.util.InterfaceInfo;
import org.jeecg.modules.doctor.util.LogUtilNew;
import org.jeecg.modules.doctor.util.PdfToImageService;
import org.jeecg.modules.doctor.util.RequestUtil;
import org.jeecg.modules.doctor.vo.LISApplyInfo;
import org.jeecg.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lihaoran
 * @date 2025/9/19 22:22
 */
@Service
public class IDrServiceimpl implements IDrService {

    @Autowired
    private IPeRegisterListService peRegisterListService;
    @Autowired
    private PdfToImageService pdfToImageService;
    @Autowired
    private IPeReportDepartmentImagesService peReportDepartmentImagesService;
    @Autowired
    private IPeReportDepartmentDetailService peReportDepartmentDetailService;
    @Autowired
    private ISysDictService sysDictService;


    @Override
    public Result SearchDr(LISApplyInfo lisApplyInfo) {

        // 调用接口测试
        List<PeRegisterList> personList = peRegisterListService.list(new LambdaQueryWrapper<PeRegisterList>().in(PeRegisterList::getPatientNo, lisApplyInfo.getPatIds()));
        StringBuffer resMsg = new StringBuffer();
        for (PeRegisterList peRegisterList : personList) {
            resMsg.append(DrSearchFunction(peRegisterList));
        }
        return Result.ok(resMsg.toString());
    }

    @Transactional(rollbackFor = Exception.class)
    public String DrSearchFunction(PeRegisterList peRegisterList) {
        // 记录接口请求日志
        LogUtilNew log = LogUtilNew.getInstance(InterfaceInfo.OBTAIN_THE_PATIENT_DR, peRegisterList);
        // log是否成功标志
        boolean flag = true;
        String res = "";
        try {
            List<PeReportDepartmentDetail> list1 = peReportDepartmentDetailService.list(new LambdaQueryWrapper<PeReportDepartmentDetail>().eq(PeReportDepartmentDetail::getPatientNo, peRegisterList.getPatientNo()));
            List<PeReportDepartmentDetail> collect = list1.stream().filter(item -> item.getItemNo().equals("LNCT8200") || item.getItemNo().equals("LNCT8200B")).collect(Collectors.toList());
            Map<String, Object> map1 = new HashMap<>();
            // 取出字典中的医院id的对应的编码
            List<DictModel> hospitalIds = sysDictService.getDictItems("hospital_id");
            // 医院编码
            map1.put("hospitalId", hospitalIds.get(0).getValue());
            map1.put("applyNo", peRegisterList.getDrPatientNo());
            // 请求信息封装
            log.setSendMessage(JSONUtil.parse(map1).toString());
            res = RequestUtil.go(InterfaceInfo.OBTAIN_THE_PATIENT_DR.getUrl(), InterfaceInfo.OBTAIN_THE_PATIENT_DR.getRequestType(), map1);
            // 返回信息封装
            log.setReceiveMessage(res);
            System.err.print(res);
            // 判断
            if (!JSONUtil.isJson(res)) {
                System.err.print("判断是否是json");
                throw new RuntimeException("返回信息不是json！");
            }
            System.err.print("即将要转换为bean");
            // 将返回的数据转换成为，接收类数据
            PacsResponseDr response = JSONUtil.toBean(res, PacsResponseDr.class);
            System.err.print("已经转换为转换为bean");
            log.log("返回数据映射到实体类后" + JSONUtil.toJsonStr(response));
            byte[] byteData = null;
            System.err.print("血循环早符合逻辑dr");
            System.err.print("获取的data数据" + response.getData());
            List<PacsReportDetailDTO> resposeDetail = new ArrayList<>();
            if (CollUtil.isNotEmpty(resposeDetail)) {
                byteData = pdfToImageService.convertPdfFirstPageToImage(resposeDetail.get(0).getReportPdfPath());
            }
            log.log("返回的pdf转换后的二进制结果" + Arrays.toString(byteData));
            log.success(response.getSuccess());
            // 获取Dr存储表并进行图片存储
            List<PeReportDepartmentImages> list = peReportDepartmentImagesService.list(new LambdaQueryWrapper<PeReportDepartmentImages>().eq(PeReportDepartmentImages::getPatientNo, peRegisterList.getPatientNo()).eq(PeReportDepartmentImages::getDepartmentId, 10));
            // 不是盲目的叠加，二是替换之前的图片。
            PeReportDepartmentImages peReportDepartmentImages = new PeReportDepartmentImages();
            if (CollUtil.isNotEmpty(list)) {
                peReportDepartmentImages = list.get(0);
            }
            peReportDepartmentImages.setPatientNo(peRegisterList.getPatientNo());
            peReportDepartmentImages.setImageIndex(1);
            peReportDepartmentImages.setImageData(byteData);
            peReportDepartmentImages.setDepartmentId(10);
            log.log("pat" + peReportDepartmentImages.getPatientNo() + "imgindex" + peReportDepartmentImages.getImageIndex() + "imgdata" + peReportDepartmentImages.getBaseSixFour());
            peReportDepartmentImagesService.saveOrUpdate(peReportDepartmentImages);
            for (PeReportDepartmentDetail peReportDepartmentDetail : collect) {
                if (peReportDepartmentDetail.getItemNo().equals("LNCT8200")) {
                    // 结论
                    peReportDepartmentDetail.setPeResult(resposeDetail.get(0).getConclusion());
                } else {
                    // 描述
                    peReportDepartmentDetail.setPeResult(resposeDetail.get(0).getFinding());
                }
                // 正确写法：update(实体对象, 查询条件Wrapper)
                peReportDepartmentDetailService.update(peReportDepartmentDetail, // 要更新的实体（设置需要修改的字段值）
                        new LambdaQueryWrapper<PeReportDepartmentDetail>().eq(PeReportDepartmentDetail::getPatientNo, peRegisterList.getPatientNo()) // 第一个唯一标识字段
                                .eq(PeReportDepartmentDetail::getItemNo, peReportDepartmentDetail.getItemNo()) // 第二个唯一标识字段
                );
            }
        } catch (Exception e) {
            log.log("方法报错" + e.getMessage());
            log.success(false);
            e.printStackTrace();
        } finally {
            log.saveLog();
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer returnImageIndex(List<PeReportDepartmentImages> list, Integer departmentId) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        Map<Integer, List<PeReportDepartmentImages>> collect = list.stream().collect(Collectors.groupingBy(PeReportDepartmentImages::getDepartmentId));
        List<PeReportDepartmentImages> peReportDepartmentImages = collect.get(departmentId);
        if (CollUtil.isNotEmpty(peReportDepartmentImages)) {
            return peReportDepartmentImages.size();
        } else {
            return 0;
        }
    }


}
