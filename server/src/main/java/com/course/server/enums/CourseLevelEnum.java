package com.course.server.enums;

/**
 * 枚举值: 开发人员使用
 * code: 程序中使用(前后端传输数据,到数据库村存储都是使用code)
 * desc: 用户使用
 */
public enum CourseLevelEnum {
    /**
     * 枚举值
     */
    ONE("1","初级"),
    TWO("2","中级"),
    THREE("3","高级");

    private String code;
    private String desc;

    /**
     * 全参构造方法
     * @param code
     * @param desc
     */
    CourseLevelEnum(String code, String desc) {
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


}
