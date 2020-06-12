package com.course.server.mapper;

import com.course.server.domain.Role_User;
import com.course.server.domain.Role_UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Role_UserMapper {
    long countByExample(Role_UserExample example);

    int deleteByExample(Role_UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role_User record);

    int insertSelective(Role_User record);

    List<Role_User> selectByExample(Role_UserExample example);

    Role_User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role_User record, @Param("example") Role_UserExample example);

    int updateByExample(@Param("record") Role_User record, @Param("example") Role_UserExample example);

    int updateByPrimaryKeySelective(Role_User record);

    int updateByPrimaryKey(Role_User record);
}