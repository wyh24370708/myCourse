package com.course.server.service;

import com.course.server.domain.Resource;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface ResourceService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Resource> pageDto);

    /**
     * 新增
     */
    void save(ResourceDto ResourceDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 保存资源json
     */
    void saveJson(String resource_json);
}