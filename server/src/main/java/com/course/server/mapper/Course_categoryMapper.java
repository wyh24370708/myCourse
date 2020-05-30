package com.course.server.mapper;

import com.course.server.domain.Course_category;
import com.course.server.domain.Course_categoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Course_categoryMapper {
    long countByExample(Course_categoryExample example);

    int deleteByExample(Course_categoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(Course_category record);

    int insertSelective(Course_category record);

    List<Course_category> selectByExample(Course_categoryExample example);

    Course_category selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Course_category record, @Param("example") Course_categoryExample example);

    int updateByExample(@Param("record") Course_category record, @Param("example") Course_categoryExample example);

    int updateByPrimaryKeySelective(Course_category record);

    int updateByPrimaryKey(Course_category record);
}