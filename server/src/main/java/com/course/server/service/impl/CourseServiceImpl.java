package com.course.server.service.impl;

import com.course.server.domain.Course;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.service.CourseService;
import com.course.server.service.Course_categoryService;
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
public class CourseServiceImpl implements CourseService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private MyCourseMapper myCourseMapper;
    @Resource
    private Course_categoryService course_categoryService;

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
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("sort desc");
        //2. 设置total属性
        List<Course> courseListDB = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courseListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<CourseDto> courseDtoList = CopyUtil.copyList(courseListDB, CourseDto.class);
        pageDto.setList(courseDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(CourseDto courseDto) {
        //保存课程基本信息
        // Course course = new Course();
        // BeanUtils.copyProperties(courseDto,course);
        //工具类转换
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())){
            //插入
            this.insert(course);
        }else{
            this.update(course);
        }

        //批量-保存课程分类
        course_categoryService.saveBatch(courseDto.getId(), courseDto.getCategorys());
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新时长
     * @param id
     */
    @Override
    public void updateTime(String id) {
        LOG.info("课程总时长:{}",id);
        myCourseMapper.updateTime(id);
    }

    //插入
    private void insert(Course course) {
        Date date = new Date();
        course.setCreatedAt(date);
        //设定新增的id的uuid值
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }
    //更新
    private void update(Course course) {
        Date date = new Date();
        course.setUpdatedAt(date);
        courseMapper.updateByPrimaryKey(course);
    }
    
}    