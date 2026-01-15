package org.jeecg.modules.doctor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description: 东华原处方传入接口响应实体类
 * @Author: lihaoran
 * @Date: 2026-01-10
 */
@Data
public class DhyPrescriptionResponse {

    /**
     * 返回结果编码
     * 当且仅当为 0 时，表示推送成功
     */
    @JsonProperty("dhy_error_code")
    private String dhyErrorCode;

    /**
     * 返回结果说明
     * 成功默认为 OK
     */
    @JsonProperty("dhy_error_msg")
    private String dhyErrorMsg;

    /**
     * 判断是否成功
     * @return true-成功，false-失败
     */
    public boolean isSuccess() {
        return "0".equals(dhyErrorCode);
    }
}