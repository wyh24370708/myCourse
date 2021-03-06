package com.course.business.controller.web;

import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/web/course")
public class CourseWebController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseWebController.class);
    public static final String BUSINESS_NAME = "web课程";

    @Resource
    private CourseService courseService;

    /**
     * 列表查询，查询最新的3门已发布的课程
     * @return
     */
    @GetMapping("/list-new")
    public ResponseDto listNew(){
        ResponseDto responseDto = new ResponseDto();
        PageDto pageDto = new PageDto();
        pageDto.setPageNum(1);
        pageDto.setPageSize(3);
        List<CourseDto> courseDtoList = courseService.listNew(pageDto);
        responseDto.setContent(courseDtoList);
        return responseDto;
    }

    /**
     * 全部课程
     * @return
     */
    @PostMapping("/list")
    public ResponseDto listCourse(@RequestBody CoursePageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        pageDto.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.findAll(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 查找课程详情,包括大章,小节,讲师,课程内容
     */
    @GetMapping("/find/{id}")
    public ResponseDto findCourse(@PathVariable("id") String courseId){
        ResponseDto responseDto = new ResponseDto();
        CourseDto courseDto = courseService.findCourse(courseId);
        responseDto.setContent(courseDto);
        return responseDto;
    }


}
