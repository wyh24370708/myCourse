package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.Course;
import com.course.server.dto.ChapterDto;

import java.util.List;

public interface ChapterService {

    /**
     * 查询所有
     * @return
     */
    List<ChapterDto> findAll();
}
