package com.course.server.service;

import com.course.server.domain.User;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface UserService {

    /**
     * 【登录】
     */
    LoginUserDto login(UserDto userDto);

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<User> pageDto);

    /**
     * 新增
     */
    void save(UserDto UserDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 【密码重置保存】
     */
    void savePwd(UserDto userDto);

    /**
     * 【基于用户查询角色(已分配和未分配)】
     */
    UserDto selectRole(String userId);

    /**
     * 【保存用户分配的角色】
     */
    void saveGrantedRole(UserDto userDto);
}