package com.course.server.service.impl;

import com.course.server.domain.Course_content_file;
import com.course.server.domain.Course_content_fileExample;
import com.course.server.dto.Course_content_fileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.Course_content_fileMapper;
import com.course.server.service.Course_content_fileService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Course_content_fileServiceImpl implements Course_content_fileService {

    @Resource
    private Course_content_fileMapper course_content_fileMapper;

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
        Course_content_fileExample course_content_fileExample = new Course_content_fileExample();
        //2. 设置total属性
        List<Course_content_file> course_content_fileListDB = course_content_fileMapper.selectByExample(course_content_fileExample);
        PageInfo<Course_content_file> pageInfo = new PageInfo<>(course_content_fileListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<Course_content_fileDto> course_content_fileDtoList = CopyUtil.copyList(course_content_fileListDB, Course_content_fileDto.class);
        pageDto.setList(course_content_fileDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(Course_content_fileDto course_content_fileDto) {
        // Course_content_file course_content_file = new Course_content_file();
        // BeanUtils.copyProperties(course_content_fileDto,course_content_file);
        //工具类转换
        Course_content_file course_content_file = CopyUtil.copy(course_content_fileDto, Course_content_file.class);
        if (StringUtils.isEmpty(course_content_fileDto.getId())){
            //插入
            this.insert(course_content_file);
        }else{
            this.update(course_content_file);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        course_content_fileMapper.deleteByPrimaryKey(id);
    }

    private void insert(Course_content_file course_content_file) {
        //设定新增的id的uuid值
        course_content_file.setId(UuidUtil.getShortUuid());
        course_content_fileMapper.insert(course_content_file);
    }

    private void update(Course_content_file course_content_file) {
        course_content_fileMapper.updateByPrimaryKey(course_content_file);
    }
    
}    