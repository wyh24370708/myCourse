package com.course.server.service;

import com.course.server.domain.Course_content_file;
import com.course.server.dto.Course_content_fileDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface Course_content_fileService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Course_content_file> pageDto);

    /**
     * 新增
     */
    void save(Course_content_fileDto Course_content_fileDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
    
}