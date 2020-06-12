package com.course.server.service;

import com.course.server.domain.Role;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Role> pageDto);

    /**
     * 新增
     */
    void save(RoleDto RoleDto);

    /**
     * 保存角色资源
     * @param roleDto
     */
    void saveResource(RoleDto roleDto);

    /**
     * 查询已勾选的角色资源
     * @param roleId
     * @return
     */
    List<String> findCheckResource(String roleId);

    /**
     * 删除
     * @param id
     */
    void delete(String id);


}