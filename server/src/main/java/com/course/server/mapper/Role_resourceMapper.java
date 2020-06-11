package com.course.server.mapper;

import com.course.server.domain.Role_resource;
import com.course.server.domain.Role_resourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Role_resourceMapper {
    long countByExample(Role_resourceExample example);

    int deleteByExample(Role_resourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role_resource record);

    int insertSelective(Role_resource record);

    List<Role_resource> selectByExample(Role_resourceExample example);

    Role_resource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role_resource record, @Param("example") Role_resourceExample example);

    int updateByExample(@Param("record") Role_resource record, @Param("example") Role_resourceExample example);

    int updateByPrimaryKeySelective(Role_resource record);

    int updateByPrimaryKey(Role_resource record);
}