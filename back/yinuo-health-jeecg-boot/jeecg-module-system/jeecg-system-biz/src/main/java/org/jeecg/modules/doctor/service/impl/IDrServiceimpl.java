package org.jeecg.modules.doctor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.doctor.entity.PeRegisterList;
//import org.jeecg.modules.doctor.entity.PeReportDepartmentImages;
//import org.jeecg.modules.doctor.service.IPeReportDepartmentImagesService;
import org.jeecg.modules.doctor.entity.PatCheckQueryRequest;
import org.jeecg.modules.doctor.entity.PatCheckResponse;
import org.jeecg.modules.doctor.entity.PatCheckRoot;
import org.jeecg.modules.doctor.entity.VO.PatientCheckVO;
import org.jeecg.modules.doctor.service.IDrService;
import org.jeecg.modules.doctor.service.IPeRegisterListService;
import org.jeecg.modules.doctor.util.InterfaceInfo;
import org.jeecg.modules.doctor.util.LogUtilNew;
import org.jeecg.modules.doctor.util.PdfToImageService;
import org.jeecg.modules.doctor.util.RequestUtil;
import org.jeecg.modules.doctor.vo.LISApplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
//    @Autowired
//    private IPeReportDepartmentImagesService peReportDepartmentImagesService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result SearchDr(LISApplyInfo lisApplyInfo) {

        // 调用接口测试
        List<PeRegisterList> personList = peRegisterListService.listByIds(lisApplyInfo.getPatIds());
        StringBuffer resMsg = new StringBuffer();
        for (PeRegisterList peRegisterList : personList) {
            resMsg.append(DrSearchFunction(peRegisterList));
        }
        return Result.ok(resMsg.toString());
    }

    @Transactional(rollbackFor = Exception.class)
    public String DrSearchFunction(PeRegisterList peRegisterList) {
        // 记录接口请求日志
        LogUtilNew log = LogUtilNew.getInstance(InterfaceInfo.OBTAIN_THE_PATIENT_EXAMINATION_INFORMATION, peRegisterList);
        // log是否成功标志
        boolean flag = true;
        String res = "";
        try {
            PatCheckRoot patCheckRoot = new PatCheckRoot();
            PatCheckQueryRequest patCheckQueryRequest = new PatCheckQueryRequest();
            patCheckQueryRequest.setStartDate(DateUtil.format(peRegisterList.getPeDate(), "yyyy-MM-dd HH:mm:ss"));
            patCheckQueryRequest.setEndDate(DateUtil.format(DateUtil.offsetDay(peRegisterList.getPeDate(), 7), "yyyy-MM-dd HH:mm:ss"));
            patCheckQueryRequest.setFilterPrintFlag(0);
            if (StrUtil.isNotEmpty(peRegisterList.getPatId())) {
                patCheckQueryRequest.setPatId(Integer.parseInt(peRegisterList.getPatId()));
            }
            patCheckQueryRequest.setIdcardNo(peRegisterList.getCardNo());
            patCheckRoot.setRoot(patCheckQueryRequest);
            Map<String, Object> map1 = BeanUtil.beanToMap(patCheckQueryRequest);
            // 请求信息封装
            log.setSendMessage(JSONUtil.parse(map1).toString());
            res = RequestUtil.go(InterfaceInfo.OBTAIN_THE_PATIENT_EXAMINATION_INFORMATION.getUrl(), InterfaceInfo.OBTAIN_THE_PATIENT_EXAMINATION_INFORMATION.getRequestType(), map1);
            // 返回信息封装
            log.setReceiveMessage(res);
            // 判断
            if (!JSONUtil.isJson(res)) {
                throw new RuntimeException("返回信息不是json！");
            }
            // 将返回的数据转换成为，接收类数据
            PatCheckResponse response = JSONUtil.toBean(res, PatCheckResponse.class);
            log.log("返回数据映射到实体类后"+JSONUtil.toJsonStr(response));
            byte[] byteData = null;
            for (PatientCheckVO datum : response.getData()) {
                if (datum.getStuEquipmentName().contains("DR")){
                byteData = pdfToImageService.convertPdfFirstPageToImage(datum.getReportPdfPath());
                }
            }
//             byte[] byteData = pdfToImageService.convertPdfFirstPageToImage("https://www.pwithe.com/Public/Upload/download/20170211/589ebf8e5bb13.pdf");
            log.log("返回的pdf转换后的二进制结果" + Arrays.toString(byteData));
            log.success(response.getSuccess());
            // 获取Dr存储表并进行图片存储
//            List<PeReportDepartmentImages> list = peReportDepartmentImagesService.list(new LambdaQueryWrapper<PeReportDepartmentImages>().eq(PeReportDepartmentImages::getPatientNo, peRegisterList.getPatientNo()));
//            PeReportDepartmentImages peReportDepartmentImages = new PeReportDepartmentImages();
//            peReportDepartmentImages.setPatientNo(peRegisterList.getPatientNo());
//            peReportDepartmentImages.setImageIndex(returnImageIndex(list,10)+1);
//            peReportDepartmentImages.setImageData(byteData);
//            peReportDepartmentImages.setDepartmentId(10);
//            log.log("pat"+peReportDepartmentImages.getPatientNo()+"imgindex"+peReportDepartmentImages.getImageIndex()+"imgdata"+ peReportDepartmentImages.getBaseSixFour());
//            peReportDepartmentImagesService.save(peReportDepartmentImages);
        } catch (Exception e) {
            log.log("方法报错"+e.getMessage());
            log.success(false);
            e.printStackTrace();
        } finally {
            log.saveLog();
        }
        return res;
    }

//    @Transactional(rollbackFor = Exception.class)
//    public Integer returnImageIndex(List<PeReportDepartmentImages> list,Integer departmentId){
//        if (list == null || list.size() == 0) {
//            return 0;
//        }
//        Map<Integer, List<PeReportDepartmentImages>> collect = list.stream().collect(Collectors.groupingBy(PeReportDepartmentImages::getDepartmentId));
//        List<PeReportDepartmentImages> peReportDepartmentImages = collect.get(departmentId);
//        return peReportDepartmentImages.size();
//    }


}
