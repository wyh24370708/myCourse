package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有
     * @return
     */
    List<CategoryDto> all();

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Category> pageDto);

    /**
     * 新增
     */
    void save(CategoryDto CategoryDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
    
}