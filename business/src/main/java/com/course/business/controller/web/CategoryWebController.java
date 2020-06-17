package com.course.business.controller.web;

import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.service.CategoryService;
import com.course.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/web/category")
public class CategoryWebController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryWebController.class);
    public static final String BUSINESS_NAME = "web分类";

    @Resource
    private CategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @PostMapping("/all")
    public ResponseDto all(){
        ResponseDto responseDto = new ResponseDto();
        List<CategoryDto> categoryDtoList = categoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }

}
