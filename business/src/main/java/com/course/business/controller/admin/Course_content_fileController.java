package com.course.business.controller.admin;

import com.course.server.dto.Course_content_fileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.Course_content_fileService;
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
@RequestMapping("/admin/course_content_file")
public class Course_content_fileController {

    private static final Logger LOG = LoggerFactory.getLogger(Course_content_fileController.class);
    public static final String BUSINESS_NAME = "课程内容文件";

    @Resource
    private Course_content_fileService course_content_fileService;

    /**
     * 查询所有
     * @param courseId
     * @return
     */
    @GetMapping("/list/{courseId}")
    public ResponseDto findAll(@PathVariable("courseId") String courseId) {
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();
        LOG.info("查询课程内容文件结果:{}",courseId);
        List<Course_content_fileDto> course_content_fileDtoList = course_content_fileService.findAll(courseId);
        //统一返回格式 end
        responseDto.setContent(course_content_fileDtoList);
        return responseDto;
    }

    /**
     * 新增
     * @param course_content_fileDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody Course_content_fileDto course_content_fileDto) {
        //统一返回格式 start
        ResponseDto<Course_content_fileDto> responseDto = new ResponseDto<Course_content_fileDto>();

        //保存校验
        ValidatorUtil.require(course_content_fileDto.getCourseId(), "课程id");
        ValidatorUtil.length(course_content_fileDto.getUrl(), "地址", 1, 100);
        ValidatorUtil.length(course_content_fileDto.getName(), "文件名", 1, 100);

        course_content_fileService.save(course_content_fileDto);
        LOG.info("保存课程内容文件结果:{}",course_content_fileDto);

        //统一返回格式 end
        responseDto.setContent(course_content_fileDto);
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
        course_content_fileService.delete(id);
        LOG.info("删除课程内容文件的id:{}",id);
        return responseDto;
    }

}
