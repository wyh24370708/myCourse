package com.course.server.service.impl;

import com.course.server.domain.Chapter;
import com.course.server.domain.Course;
import com.course.server.mapper.ChapterMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.service.ChapterService;
import com.course.server.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    @Override
    public List<Chapter> findAll() {
        return chapterMapper.selectByExample(null);
    }
}
