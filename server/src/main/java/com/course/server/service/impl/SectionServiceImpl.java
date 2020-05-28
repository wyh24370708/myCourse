package com.course.server.service.impl;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.service.CourseService;
import com.course.server.service.SectionService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class SectionServiceImpl implements SectionService {

    private static final Logger LOG = LoggerFactory.getLogger(SectionServiceImpl.class);
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private CourseService courseService;

    /**
     * 查询所有
     * @return
     */
    @Override
    public void findAll(SectionPageDto sectionPageDto) {
        //分页
        PageHelper.startPage(sectionPageDto.getPageNum(), sectionPageDto.getPageSize());
        //查询数据库

        //1. 倒叙排列
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("sort desc");
        SectionExample.Criteria exampleCriteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())){
            exampleCriteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())){
            exampleCriteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        //2. 设置total属性
        List<Section> sectionListDB = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionListDB);
        sectionPageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionListDB, SectionDto.class);
        sectionPageDto.setList(sectionDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(SectionDto sectionDto) {
        // Section section = new Section();
        // BeanUtils.copyProperties(sectionDto,section);
        //工具类转换
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())){
            //插入
            this.insert(section);
        }else{
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }

    private void insert(Section section) {
        Date date = new Date();
        section.setCreatedAt(date);
        //设定新增的id的uuid值
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    private void update(Section section) {
        Date date = new Date();
        section.setUpdatedAt(date);
        sectionMapper.updateByPrimaryKey(section);
    }
    
}    