package com.course.server.enums;

/**
 * 枚举值: 开发人员使用
 * code: 程序中使用(前后端传输数据,到数据库村存储都是使用code)
 * desc: 用户使用
 */
public enum SectionChargeEnum {
    /**
     * 枚举值
     */
    CHARGE("C","收费"),
    FREE("C","免费");

    private String code;
    private String desc;

    /**
     * 全参构造方法
     * @param code
     * @param desc
     */
    SectionChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SectionChargeEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
