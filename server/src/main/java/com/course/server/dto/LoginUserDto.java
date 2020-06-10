package com.course.server.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class LoginUserDto {

    /**
     * id
     */
    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;

    /**
     * 登陆凭证
     */
    private String token;


}