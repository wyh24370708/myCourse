package com.course.server.service.impl;

import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
import com.course.server.service.TeacherService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

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
        TeacherExample teacherExample = new TeacherExample();
        //2. 设置total属性
        List<Teacher> teacherListDB = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherListDB, TeacherDto.class);
        pageDto.setList(teacherDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(TeacherDto teacherDto) {
        // Teacher teacher = new Teacher();
        // BeanUtils.copyProperties(teacherDto,teacher);
        //工具类转换
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if (StringUtils.isEmpty(teacherDto.getId())){
            //插入
            this.insert(teacher);
        }else{
            this.update(teacher);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    private void insert(Teacher teacher) {
        //设定新增的id的uuid值
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }
    
}    