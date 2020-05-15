package com.course.server.service.impl;

import com.course.server.mapper.CourseMapper;
import com.course.server.domain.Course;
import com.course.server.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper testMapper;

    @Override
    public List<Course> testFindAll() {
        return testMapper.testFindAll();
    }
}
