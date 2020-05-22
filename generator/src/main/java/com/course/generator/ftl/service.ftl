package com.course.server.service;

import com.course.server.domain.${Domain};
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface ${Domain}Service {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<${Domain}> pageDto);

    /**
     * 新增
     */
    void save(${Domain}Dto ${Domain}Dto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
    
}