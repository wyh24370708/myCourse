package com.course.business.controller;

import com.course.server.domain.Chapter;
import com.course.server.service.ChapterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Chapter> findAll(){
        List<Chapter> chapterList = chapterService.findAll();
        return chapterList;
    }

}
