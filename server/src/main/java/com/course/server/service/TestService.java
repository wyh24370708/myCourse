package com.course.server.service;

import com.course.server.domain.Course;

import java.util.List;

public interface TestService {

    /**
     * 查询所有
     * @return
     */
    List<Course> testFindAll();
}
