package org.jeecg.modules.doctor.util;

public enum StaticValue {
      ZERO("0","数字 0"),
      ONE("1","数字 1"),
      TWO("2","数字 2"),
      FAIL("fail","失败"),
      SUCCESS("success","成功"),
      MAN("1","男"),
      WOMAN("2","女"),
      ORG_ID("10353","机构ID"),
      HOSPITAL_ID("10353001","医院id"),
      DEPT_ID("175","科室id"),
      DEPT_NAME("社区门诊","科室名称"),
      DOC_ID("2022658","医生id"),
      DOC_NAME("程亮","医生名称"),
      WU("无","无"),
      APP_KEY("app6096163592757055371","appKey"),
      APP_SECRET("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALR+DjmEgIGx0ZMgX+7iCTlF5/4dOzKwJBZf+DHNiq6Z/OeDIlMOL/D3vgWv76Fg4AWiCBG4ucqU5G1JUu2IS4wUOAFMsHVGbSWORl587vtXf4z9JxiB1epMInDKpBYiGXecC1NS1feyRBPMKZY9Wcr1GWi/ljxMtzARad9tEe1nAgMBAAECgYEApAGzLUKgfrk0pUsSwP91wCwsfTTUmN+DOy2jWqXZsYRNUZVP+EZ4+64yZfqTdYGQrq2oRWoksNcQpdmt2Bc0BSJsX7DbBz9Cd/rwDP/m/d96MB68RK64Y1/bXNsAevL5GJ8Y/JGn3/V08w5Qly9Ot495fxW0Wd0QqEdqaomo6QECQQDottwk3jpi2TcrB6HpvPJ0vz65QQLMh2LtMLO9+wh6OPdFp9qW8CAisPlO8D2P+y00/p3uAELv6gHo6OLylTaBAkEAxo1+xbxiBjtkLsGSbNL2bceTqEmux/9vIPF8b7Zz+YdjYBe8dIA3A9vJNtc291gqCseNLl2hjpafTr0v9gQ/5wJACqKIrvqk6m2I0uOXZ6ol3mX7BNZOvXAWekZ2gGEfgw5lZn6EQ8+XeF5kFlJuTc0pxLk6GG1Uocgs7CrwbCubgQJBAI//ipOJ3mW6qRun07QNBB/2AEaquZHe1Q/FU4QJhO7Rm+bTECBF5KBtw+58Ayc7z7Hlq6SWz1aEMqF+X8xCpL0CQCdP1JoaaGkYrRTVYXQi0G1ttK5Sqchh2FssyOWRHq5S9KH/dc9G2hWrn2Ci9WxvKxuI607MHDGVPSMkpRlJ9qo=","app密钥"),



    ;
    private String code;
    private String text;

    StaticValue(String code) {
        this.code = code;
    }

    StaticValue(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
