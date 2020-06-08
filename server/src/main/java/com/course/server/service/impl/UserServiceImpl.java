package com.course.server.service.impl;

import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.UserMapper;
import com.course.server.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

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
        UserExample userExample = new UserExample();
        //2. 设置total属性
        List<User> userListDB = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<UserDto> userDtoList = CopyUtil.copyList(userListDB, UserDto.class);
        pageDto.setList(userDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(UserDto userDto) {
        // User user = new User();
        // BeanUtils.copyProperties(userDto,user);
        //工具类转换
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isEmpty(userDto.getId())){
            //插入
            this.insert(user);
        }else{
            this.update(user);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    private void insert(User user) {
        Date date = new Date();
        user.setCreatedAt(date);
        //设定新增的id的uuid值
        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }

    private void update(User user) {
        Date date = new Date();
        user.setUpdatedAt(date);
        userMapper.updateByPrimaryKey(user);
    }
    
}    