package com.course.system.service.impl;

import com.course.system.dao.TestMapper;
import com.course.system.domain.Course;
import com.course.system.service.TestService;
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
