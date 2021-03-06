package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface TeacherService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Teacher> pageDto);

    /**
     * 新增
     */
    void save(TeacherDto TeacherDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询所有 不分页
     */
    List<TeacherDto> all();

    /**]
     * 查询单个讲师的信息
     * @param id
     * @return
     */
    TeacherDto findTeacherById(String id);
}