package com.course.server.mapper;

import com.course.server.domain.Course_content_file;
import com.course.server.domain.Course_content_fileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Course_content_fileMapper {
    long countByExample(Course_content_fileExample example);

    int deleteByExample(Course_content_fileExample example);

    int deleteByPrimaryKey(String id);

    int insert(Course_content_file record);

    int insertSelective(Course_content_file record);

    List<Course_content_file> selectByExample(Course_content_fileExample example);

    Course_content_file selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Course_content_file record, @Param("example") Course_content_fileExample example);

    int updateByExample(@Param("record") Course_content_file record, @Param("example") Course_content_fileExample example);

    int updateByPrimaryKeySelective(Course_content_file record);

    int updateByPrimaryKey(Course_content_file record);
}