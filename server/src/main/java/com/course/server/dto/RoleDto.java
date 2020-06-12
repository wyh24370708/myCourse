package com.course.server.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    /**
     * id
     */
    private String id;

    /**
     * 角色
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 资源集合
     */
    List<String> resourceIds;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoleDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", resourceIds=").append(resourceIds);
        sb.append('}');
        return sb.toString();
    }
}