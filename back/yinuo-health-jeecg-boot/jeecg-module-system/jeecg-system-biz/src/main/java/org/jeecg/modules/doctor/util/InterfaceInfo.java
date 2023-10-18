package org.jeecg.modules.doctor.util;

public enum InterfaceInfo {
    CHECK_PROJECTS("/msun-middle-business-lis/v1/lis-applies/items", "LIS检查项目查询","get","lisApply"),
    DEPARTMENTS_AND_OFFICES("/msun-middle-base-common/v1/depts", "科室信息","get","ksxx"),
    PERSON_SEARCH("/msun-middle-aggregate-patient/v1/pat-infos/10353", "档案查询","get","dacx"),
    PERSON_CREATE("/msun-middle-aggregate-patient/v1/pat-infos/patients", "创建档案","post","createda"),
    LIS_APPLY("/msun-middle-business-lis/v1/lis-applies", "LIS检验申请","post","lisApply"),
    BAR_CODE_BUILD("/msun-middle-business-lis/v1/lis-barcodes", "条码生成","post","barCodeBuild"),
    REPORT_ID_SEARCH("/msun-middle-business-lis/v1/lis-reports", "查询报告ID","get","reportIdSearch"),
    REPORT_DETAIL_SEARCH("/msun-middle-business-lis/v1/lis-reports/details", "报告明细查询","get","reportDetailSearch"),
    LOG_TEST("log_test", "日志测试","post","log_test"),

    ;
    // url 复制
    private String url;
    // 接口备注
    private String remark;
    // 接口备注
    private String requestType;
    // 预留数值字段
    private String code;

    InterfaceInfo(String url) {
        this.url = url;
    }

    InterfaceInfo(String url, String remark) {
        this.url = url;
        this.remark = remark;
    }

    InterfaceInfo(String url, String remark, String requestType) {
        this.url = url;
        this.remark = remark;
        this.requestType = requestType;
    }
    InterfaceInfo(String url, String remark,String requestType, String code) {
        this.url = url;
        this.remark = remark;
        this.requestType = requestType;
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
