package com.course.server.service;

import com.course.server.domain.Course_category;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.Course_categoryDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface Course_categoryService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Course_category> pageDto);

    /**
     * 新增
     */
    void save(Course_categoryDto Course_categoryDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 批量保存
     */
    void saveBatch(String courseId, List<CategoryDto> dtoList);

    /**
     * 编辑U回显课程分类
     */
    List<Course_categoryDto> queryCategoryList(String courseId);
}