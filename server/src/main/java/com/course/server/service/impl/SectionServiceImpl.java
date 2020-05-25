package com.course.server.service.impl;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.service.SectionService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class SectionServiceImpl implements SectionService {

    @Resource
    private SectionMapper sectionMapper;

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
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("sort desc");
        //2. 设置total属性
        List<Section> sectionListDB = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionListDB, SectionDto.class);
        pageDto.setList(sectionDtoList);
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