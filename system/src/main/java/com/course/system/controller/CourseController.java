package com.course.system.controller;

import com.course.server.domain.Course;
import com.course.server.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CourseController {

    @Resource
    private CourseService testService;

    /**
     * 测试路由转发发
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "success";
    }

    /**
     * 测试mybatis
     */
    @RequestMapping("/testFindAll")
    @ResponseBody
    public List<Course> testFindAll(){
        List<Course> courseList = testService.testFindAll();
        return courseList;
    }

}
