package com.course.server.mapper;

import com.course.server.domain.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    /**
     * 查询所有
     * @return
     */
    List<Course> testFindAll();

}