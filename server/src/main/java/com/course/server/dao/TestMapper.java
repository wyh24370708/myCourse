package com.course.server.dao;

import com.course.server.domain.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    /**
     * 查询所有
     * @return
     */
    List<Course> testFindAll();

}
