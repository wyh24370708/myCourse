package com.course.server.service.impl;

import com.course.server.domain.Course_category;
import com.course.server.domain.Course_categoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.Course_categoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.Course_categoryMapper;
import com.course.server.service.Course_categoryService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Course_categoryServiceImpl implements Course_categoryService {

    @Resource
    private Course_categoryMapper course_categoryMapper;

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
        Course_categoryExample course_categoryExample = new Course_categoryExample();
        //2. 设置total属性
        List<Course_category> course_categoryListDB = course_categoryMapper.selectByExample(course_categoryExample);
        PageInfo<Course_category> pageInfo = new PageInfo<>(course_categoryListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<Course_categoryDto> course_categoryDtoList = CopyUtil.copyList(course_categoryListDB, Course_categoryDto.class);
        pageDto.setList(course_categoryDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(Course_categoryDto course_categoryDto) {
        // Course_category course_category = new Course_category();
        // BeanUtils.copyProperties(course_categoryDto,course_category);
        //工具类转换
        Course_category course_category = CopyUtil.copy(course_categoryDto, Course_category.class);
        if (StringUtils.isEmpty(course_categoryDto.getId())){
            //插入
            this.insert(course_category);
        }else{
            this.update(course_category);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        course_categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量保存
     */
    @Override
    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        //清空课程分类数据
        Course_categoryExample example = new Course_categoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        course_categoryMapper.deleteByExample(example);
        //新增
        for (CategoryDto categoryDto : dtoList) {
            Course_category course_category = new Course_category();
            course_category.setId(UuidUtil.getShortUuid());
            course_category.setCourseId(courseId);
            course_category.setCategoryId(categoryDto.getId());
            insert(course_category);
        }
    }

    private void insert(Course_category course_category) {
        //设定新增的id的uuid值
        course_category.setId(UuidUtil.getShortUuid());
        course_categoryMapper.insert(course_category);
    }

    private void update(Course_category course_category) {
        course_categoryMapper.updateByPrimaryKey(course_category);
    }
    
}    