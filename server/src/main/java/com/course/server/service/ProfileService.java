package com.course.server.service;

import com.course.server.domain.Profile;
import com.course.server.dto.ProfileDto;
import com.course.server.dto.PageDto;

import java.util.List;

public interface ProfileService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Profile> pageDto);

    /**
     * 新增
     */
    void save(ProfileDto ProfileDto);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 分片检查 上传
     */
    ProfileDto findByKey(String key);
}