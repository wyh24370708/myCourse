package com.course.server.service;

import com.course.server.domain.Role_resource;
import com.course.server.dto.Role_resourceDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface Role_resourceService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Role_resource> pageDto);

    /**
     * 新增
     */
    void save(Role_resourceDto Role_resourceDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
    
}