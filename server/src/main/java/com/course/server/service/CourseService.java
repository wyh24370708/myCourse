package com.course.server.service;

import com.course.server.domain.Course;
import com.course.server.dto.*;

import java.util.List;

public interface CourseService {

    /**
     * 查询所有
     * @return
     */
    void findAll(CoursePageDto pageDto);

    /**
     * 新增
     */
    void save(CourseDto CourseDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 更新课程内视频总时长
     */
    void updateTime(String id);

    /**
     * 查询富文本编辑器
     */
    Course_contentDto findContent(String courseId);

    /**
     * 富文本编辑器  保存课程内容
     */
    void saveContent(Course_contentDto contentDto);

    /**
     * 【更新排序】
     */
    void uodateSort(SortDto sortDto);

    /**
     * web
     * 列表查询，查询最新的3门已发布的课程
     * @param pageDto
     * @return
     */
    List<CourseDto> listNew(PageDto pageDto);
}