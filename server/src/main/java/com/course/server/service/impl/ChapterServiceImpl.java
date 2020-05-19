package com.course.server.service.impl;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.service.ChapterService;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    /**
     * 查询所有
     * @return
     */
    @Override
    public void findAll(PageDto pageDto) {
        //分页
        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());
        //查询数据库

        //倒叙排列
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("course_id desc");

        List<Chapter> chapterListDB = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterListDB);
        pageDto.setTotalNum(pageInfo.getTotal());

        List<ChapterDto> chapterDtos = new ArrayList<>();
        //Chapter转换成ChapterDto
        for (int i = 0, LEN = chapterListDB.size(); i < LEN; i++){
            Chapter chapter = chapterListDB.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtos.add(chapterDto);
        }
        pageDto.setList(chapterDtos);
    }

    /**
     * 新增大章
     */
    @Override
    public void save(ChapterDto chapterDto) {
        chapterDto.setId(UuidUtil.getShortUuid());
    }
}
