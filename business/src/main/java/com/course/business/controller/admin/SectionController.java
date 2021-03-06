package com.course.business.controller.admin;

import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.service.SectionService;
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
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    /**
     * 查询所有
     * @param sectionPageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto findAll(@RequestBody SectionPageDto sectionPageDto) {
        //统一返回格式 start
        ResponseDto<PageDto> responseDto = new ResponseDto<PageDto>();
        //必输项
        ValidatorUtil.require(sectionPageDto.getCourseId(),"课程");
        ValidatorUtil.require(sectionPageDto.getChapterId(),"大章");
        //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
        sectionService.findAll(sectionPageDto);
        LOG.info("查询小节结果:{}",sectionPageDto);
        //统一返回格式 end
        responseDto.setContent(sectionPageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param sectionDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto) {
        //统一返回格式 start
        ResponseDto<SectionDto> responseDto = new ResponseDto<SectionDto>();

        //保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
//        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);

        sectionService.save(sectionDto);
        LOG.info("保存小节结果:{}",sectionDto);

        //统一返回格式 end
        responseDto.setContent(sectionDto);
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
        sectionService.delete(id);
        LOG.info("删除小节的id:{}",id);
        return responseDto;
    }

}
