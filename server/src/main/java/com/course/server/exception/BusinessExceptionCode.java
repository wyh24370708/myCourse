package com.course.server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("1","用户已存在"),
    LOGIN_ERROR("-1","用户名或者密码错误"),
    ;

    private String code;
    private String desc;

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
