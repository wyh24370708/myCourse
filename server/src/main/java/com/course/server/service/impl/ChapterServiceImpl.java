package com.course.server.service.impl;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.service.ChapterService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        //1. 倒叙排列
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("course_id desc");
        //2. 设置total属性
        List<Chapter> chapterListDB = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        //转换
//        List<ChapterDto> chapterDtos = new ArrayList<>();
//            //Chapter转换成ChapterDto
//        for (int i = 0, LEN = chapterListDB.size(); i < LEN; i++){
//            Chapter chapter = chapterListDB.get(i);
//            ChapterDto chapterDto = new ChapterDto();
//            BeanUtils.copyProperties(chapter,chapterDto);
//            chapterDtos.add(chapterDto);
//        }
        //工具类转换
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterListDB, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    /**
     * 保存大章
     *
     * 新增和修改
     */
    @Override
    public void save(ChapterDto chapterDto) {
        // Chapter chapter = new Chapter();
        // BeanUtils.copyProperties(chapterDto,chapter);
        //工具类转换
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())){
            //插入
            this.insert(chapter);
        }else{
            this.update(chapter);
        }
    }
    private void insert(Chapter chapter) {
        //设定新增的id的uuid值
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }
}
