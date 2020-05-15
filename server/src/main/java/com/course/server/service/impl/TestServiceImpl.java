package com.course.server.service.impl;

import com.course.server.dao.TestMapper;
import com.course.server.domain.Course;
import com.course.server.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Override
    public List<Course> testFindAll() {
        return testMapper.testFindAll();
    }
}
