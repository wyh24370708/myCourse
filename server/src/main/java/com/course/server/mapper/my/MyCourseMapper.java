package com.course.server.mapper.my;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyCourseMapper {
    /**
     * 更新课程总时长
     * @param id
     */
    void updateTime(@Param("courseId") String id);
}
