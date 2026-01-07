package org.jeecg.modules.doctor.entity.VO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 接口返回的data节点实体类（患者挂号/缴费相关信息）
 * 适配JSON字段：patCardNo、peisTypeCode、patId等
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略JSON中未定义的字段，增强兼容性
public class PeisDataDTO  implements Serializable  {

    private static final long serialVersionUID = 1L; // 序列化版本号，避免序列化异常

    /**
     * 患者卡号
     */
    private String patCardNo;

    /**
     * PEIS类型编码
     */
    private String peisTypeCode;

    /**
     * 患者ID
     * 注：JSON中为字符串类型，若需转为Long，可添加@JsonFormat或手动转换
     */
    private String patId;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 患者卡ID
     */
    private String patCardId;

    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 主键ID
     * 注：JSON中为超大数字字符串，建议保留String类型，避免Long溢出
     */
    private String id;

    /**
     * PEIS状态
     */
    private String peisStatus;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 流水号（核心字段）
     */
    private String serialNo;

    // 可选：如果需要将patId/id转为Long类型，添加转换方法
    public Long getPatIdAsLong() {
        try {
            return patId == null ? null : Long.valueOf(patId);
        } catch (NumberFormatException e) {
            return null; // 转换失败返回null，可根据业务调整为抛异常
        }
    }

    public Long getIdAsLong() {
        try {
            // 注意：id字段值8490165623883432839超过Long最大值（9223372036854775807），转换会报错
            // 若需存储超大数字，建议用BigInteger：return new BigInteger(id);
            return id == null ? null : Long.valueOf(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}