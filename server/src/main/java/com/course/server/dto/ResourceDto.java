package com.course.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResourceDto {

    /**
     * id
     */
    private String id;

    /**
     * 名称|菜单或按钮
     */
    private String name;

    /**
     * 页面|路由
     */
    private String page;

    /**
     * 请求|接口
     */
    private String request;

    /**
     * 父id
     */
    private String parent;

    /**
     * json子节点
     */
    private List<ResourceDto> children;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResourceDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", page='").append(page).append('\'');
        sb.append(", request='").append(request).append('\'');
        sb.append(", parent='").append(parent).append('\'');
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}