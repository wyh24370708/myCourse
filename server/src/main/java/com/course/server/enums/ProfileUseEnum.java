package com.course.server.enums;

/**
 * 枚举值: 开发人员使用
 * code: 程序中使用(前后端传输数据,到数据库村存储都是使用code)
 * desc: 用户使用
 */
public enum ProfileUseEnum {
    /**
     * 枚举值
     */
    COURSE("C","课程"),
    TEACHER("T","讲师");

    private String code;
    private String desc;

    /**
     * 全参构造方法
     * @param code
     * @param desc
     */
    ProfileUseEnum(String code, String desc) {
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
        final StringBuffer sb = new StringBuffer("ProfileUseEnum{");
        sb.append("code='").append(code).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
