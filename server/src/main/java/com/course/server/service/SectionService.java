package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionPageDto;

import java.util.List;

public interface SectionService {

    /**
     * 查询所有
     * @return
     */
    void findAll(SectionPageDto sectionPageDto);

    /**
     * 新增
     */
    void save(SectionDto SectionDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
    
}