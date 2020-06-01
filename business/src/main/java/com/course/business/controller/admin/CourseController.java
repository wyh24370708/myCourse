package com.course.business.controller.admin;

import com.course.server.domain.Course_content;
import com.course.server.dto.*;
import com.course.server.service.CourseService;
import com.course.server.service.Course_categoryService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @GetMapping  get请求
 * @PostMapping post请求
 * @DeleteMapping delete的请求
 */
@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程表";

    @Resource
    private CourseService courseService;
    @Resource
    private Course_categoryService course_categoryService;

    /**
     * 查询所有
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto findAll(@RequestBody PageDto pageDto) {
        //统一返回格式 start
        ResponseDto<PageDto> responseDto = new ResponseDto<PageDto>();

        //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
        courseService.findAll(pageDto);
        LOG.info("查询课程表结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param courseDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        //统一返回格式 start
        ResponseDto<CourseDto> responseDto = new ResponseDto<CourseDto>();

        //保存校验
        ValidatorUtil.require(courseDto.getName(), "课程名称");
        ValidatorUtil.length(courseDto.getName(), "课程名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
//        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);

        courseService.save(courseDto);
        LOG.info("保存课程表结果:{}",courseDto);

        //统一返回格式 end
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        LOG.info("删除课程表的id:{}",id);
        return responseDto;
    }

    /**
     * 编辑U回显课程分类
     */
    @PostMapping("/listCategory/{id}")
    public ResponseDto listCategory(@PathVariable("id") String courseId){
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();
        List<Course_categoryDto> dtoList = course_categoryService.queryCategoryList(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    /**
     * 富文本编辑器  查询课程内容
     */
    @PostMapping("/findContent/{id}")
    public ResponseDto findContent(@PathVariable("id") String courseId){
        ResponseDto responseDto = new ResponseDto();
        Course_contentDto contentDto = courseService.findContent(courseId);
        LOG.info("查询课程内容:{}",contentDto);
        responseDto.setContent(contentDto);
        return responseDto;
    }
    /**
     * 富文本编辑器  保存课程内容
     */
    @PostMapping("/saveContent")
    public ResponseDto saveContent(@RequestBody  Course_contentDto contentDto){
        ResponseDto responseDto = new ResponseDto();
        courseService.saveContent(contentDto);
        LOG.info("保存课程内容:{}",contentDto);
        return responseDto;
    }

    /**
     * 【更新排序】
     */
    @PostMapping("/updateSort")
    public ResponseDto updateSort(@RequestBody SortDto sortDto){
        ResponseDto responseDto = new ResponseDto();
        LOG.info("更新排序:{}",sortDto);
        courseService.uodateSort(sortDto);
        return responseDto;
    }


}
