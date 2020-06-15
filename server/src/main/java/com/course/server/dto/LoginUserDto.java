package com.course.server.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;

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

    /**
     * 所有资源 用于前端界面控制
     */
    private List<ResourceDto> resources;
    /**
     * 所有资源的请求, 用于后端接口的拦截
     */
    private HashSet<String> requests;


}