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
     * 删除
     * @param id
     */
    void delete(String id);
    
}