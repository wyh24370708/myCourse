package com.course.system.controller;

import com.course.server.domain.Course;
import com.course.server.domain.Province;
import com.course.server.domain.ProvinceExample;
import com.course.server.mapper.ProvinceMapper;
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
    @Resource
    private ProvinceMapper provinceMapper;
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
    public List<Province> testFindAll(){
//        List<Course> courseList = testService.testFindAll();
//        return courseList;
        ProvinceExample pe = new ProvinceExample();
        pe.setOrderByClause("id desc");
        List<Province> provinceList = provinceMapper.selectByExample(pe);
        return provinceList;
    }

}
