package com.course.server.mapper;

import com.course.server.domain.Course_content;
import com.course.server.domain.Course_contentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Course_contentMapper {
    long countByExample(Course_contentExample example);

    int deleteByExample(Course_contentExample example);

    int deleteByPrimaryKey(String id);

    int insert(Course_content record);

    int insertSelective(Course_content record);

    List<Course_content> selectByExampleWithBLOBs(Course_contentExample example);

    List<Course_content> selectByExample(Course_contentExample example);

    Course_content selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Course_content record, @Param("example") Course_contentExample example);

    int updateByExampleWithBLOBs(@Param("record") Course_content record, @Param("example") Course_contentExample example);

    int updateByExample(@Param("record") Course_content record, @Param("example") Course_contentExample example);

    int updateByPrimaryKeySelective(Course_content record);

    int updateByPrimaryKeyWithBLOBs(Course_content record);
}