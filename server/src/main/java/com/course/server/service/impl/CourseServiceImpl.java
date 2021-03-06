package com.course.server.service.impl;

import com.course.server.domain.Course;
import com.course.server.domain.CourseExample;
import com.course.server.domain.Course_content;
import com.course.server.domain.Teacher;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.Course_contentMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.service.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Resource
    private Course_contentMapper course_contentMapper;
    @Resource
    private ChapterService chapterService;
    @Resource
    private SectionService sectionService;
    @Resource
    private TeacherService teacherService;


    /**
     * 查询所有
     * @return
     */
    @Override
    public void findAll(CoursePageDto pageDto) {
        //分页
        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());
        List<CourseDto> courseDtoList = myCourseMapper.list(pageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        pageDto.setTotalNum(pageInfo.getTotal());
        pageDto.setList(courseDtoList);

//        //查询数据库
//
//        //1. 倒叙排列
//        CourseExample courseExample = new CourseExample();
//        CourseExample.Criteria criteria = courseExample.createCriteria();
//        courseExample.setOrderByClause("sort asc");
//        //判断发布状态
//        if (!StringUtils.isEmpty(pageDto.getStatus())){
//            criteria.andStatusEqualTo(pageDto.getStatus());
//        }
//        //2. 设置total属性
//        List<Course> courseListDB = courseMapper.selectByExample(courseExample);
//        PageInfo<Course> pageInfo = new PageInfo<>(courseListDB);
//        pageDto.setTotalNum(pageInfo.getTotal());
//
//        //工具类转换
//        List<CourseDto> courseDtoList = CopyUtil.copyList(courseListDB, CourseDto.class);
//        pageDto.setList(courseDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    @Transactional
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
        course_categoryService.saveBatch(course.getId(), courseDto.getCategorys());
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

    /**
     * 查询富文本编辑器
     */
    @Override
    public Course_contentDto findContent(String courseId) {
        Course_content content = course_contentMapper.selectByPrimaryKey(courseId);
        if (content == null){
            return null;
        }
        Course_contentDto contentDto = CopyUtil.copy(content, Course_contentDto.class);
        return contentDto;
    }

    /**
     * 富文本编辑器  保存课程内容
     */
    @Override
    public void saveContent(Course_contentDto contentDto) {
        Course_content courseContent = CopyUtil.copy(contentDto, Course_content.class);
        int i = course_contentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
        if (i == 0){
            course_contentMapper.insert(courseContent);
        }
    }

    /**
     * 【更新排序】
     * @param sortDto
     */
    @Override
    public void uodateSort(SortDto sortDto) {
        //更新sort
        myCourseMapper.updateSortById(sortDto);
        int num = sortDto.getNewSort() > sortDto.getOldSort() ? 0 : 1;
        //顺序的前后启动
       switch (num){
           case 0:
               myCourseMapper.moveSortsForward(sortDto);//顺序组前移
               break;
           case 1:
               myCourseMapper.moveSortBackward(sortDto);//顺序组后移
               break;
       }
    }

    /**
     * web
     * 列表查询，查询最新的3门已发布的课程
     * @param pageDto
     * @return
     */
    @Override
    public List<CourseDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNum(),pageDto.getPageSize());
        CourseExample example = new CourseExample();
        example.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());//已发布的课程
        example.setOrderByClause("created_at desc");
        List<Course> courseList = courseMapper.selectByExample(example);
        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);
        return courseDtoList;
    }

    /**
     * web
     * 查找课程详情,包括大章,小节,讲师,课程内容
     */
    @Override
    public CourseDto findCourse(String courseId) {
        //查询课程
        Course courseDB = courseMapper.selectByPrimaryKey(courseId);
        if (courseDB == null || !courseDB.getStatus().equals(CourseStatusEnum.PUBLISH.getCode())){
            return null;
        }
        CourseDto courseDto = CopyUtil.copy(courseDB, CourseDto.class);

        //查找课程内容
        Course_content courseContent = course_contentMapper.selectByPrimaryKey(courseId);
        if (courseContent!=null){
            courseDto.setCourseContent(courseContent.getContent());
        }
        //查询大章内容
        List<ChapterDto> chapterDtos = chapterService.findChapterByCourseId(courseId);
        if (chapterDtos != null){
            courseDto.setChapters(chapterDtos);
        }
        //查询小节内容
        List<SectionDto> sectionDtos = sectionService.findSectionByCourseId(courseId);
        if (sectionDtos != null){
            courseDto.setSections(sectionDtos);
        }
        //查询讲师内容
        TeacherDto teacherDto = teacherService.findTeacherById(courseDto.getTeacherId());
        if (teacherDto != null){
            courseDto.setTeacher(teacherDto);
        }

        return courseDto;
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