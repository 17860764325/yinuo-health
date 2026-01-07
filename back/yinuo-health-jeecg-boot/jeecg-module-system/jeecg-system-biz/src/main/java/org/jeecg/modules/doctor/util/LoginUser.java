package org.jeecg.modules.doctor.util;
import lombok.Data;
import java.io.Serializable;

/**
 * 登录用户信息实体类
 * 对应 loginUser 传递格式的参数定义
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户身份id（和表里的identity_id是一个）
     */
    private Long userSysId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 医院id
     */
    private Long hospitalId;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 机构id
     */
    private Long orgId;

    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 科室id
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 主科室id（和deptId保持一致即可）
     */
    private Long mainDeptId;

    /**
     * 主科室名称（和deptName保持一致即可）
     */
    private String mainDeptName;

    // 可选：添加便捷方法，自动同步 mainDeptId/mainDeptName 与 deptId/deptName
    public void syncMainDeptInfo() {
        this.mainDeptId = this.deptId;
        this.mainDeptName = this.deptName;
    }
}