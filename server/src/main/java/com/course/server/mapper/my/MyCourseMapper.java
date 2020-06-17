package com.course.server.mapper.my;


import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.dto.SortDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyCourseMapper {
    /**
     * 更新课程总时长
     * @param id
     */
    void updateTime(@Param("courseId") String id);
    //更新排序
    void updateSortById(SortDto sortDto);
    //顺序组前移
    void moveSortsForward(SortDto sortDto);
    //顺序组后移
    void moveSortBackward(SortDto sortDto);

    /**
     * 查询所有课程,  admin控台, web前台
     *     |已发布
     *     |sort排序
     * @param pageDto
     * @return
     */
    List<CourseDto> list(@Param("pageDto") CoursePageDto pageDto);
}
