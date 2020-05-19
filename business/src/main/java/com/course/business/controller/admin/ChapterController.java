package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ChapterService;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    @Resource
    private ChapterService chapterService;

    /**
     * 查询所有大章
     * @param pageDto
     * @return
     */
    @RequestMapping("/list")
    public ResponseDto findAll(@RequestBody PageDto pageDto) {
        //统一返回格式 start
        ResponseDto<PageDto> responseDto = new ResponseDto<PageDto>();

        //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
        chapterService.findAll(pageDto);
        LOG.info("查询大章结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增大章
     * @param chapterDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto) {
        //统一返回格式 start
        ResponseDto<ChapterDto> responseDto = new ResponseDto<ChapterDto>();

        chapterService.save(chapterDto);
        LOG.info("保存大章结果:{}",chapterDto);

        //统一返回格式 end
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    /**
     * 删除大章
     * @param chapterDto
     */
    @RequestMapping("/delete")
    public ResponseDto delete(@RequestBody ChapterDto chapterDto){
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(chapterDto.getId());
        LOG.info("删除大章的id:{}",chapterDto.getId());
        return responseDto;
    }

}
