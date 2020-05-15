package com.course.system.controller;

import com.course.system.domain.Course;
import com.course.system.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

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
