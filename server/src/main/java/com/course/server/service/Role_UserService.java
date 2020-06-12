package com.course.server.service;

import com.course.server.domain.Role_User;
import com.course.server.dto.Role_UserDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface Role_UserService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Role_User> pageDto);

    /**
     * 新增
     */
    void save(Role_UserDto Role_UserDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
    
}