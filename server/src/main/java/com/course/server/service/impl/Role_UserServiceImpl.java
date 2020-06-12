package com.course.server.service.impl;

import com.course.server.domain.Role_User;
import com.course.server.domain.Role_UserExample;
import com.course.server.dto.Role_UserDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.Role_UserMapper;
import com.course.server.service.Role_UserService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Role_UserServiceImpl implements Role_UserService {

    @Resource
    private Role_UserMapper role_UserMapper;

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
        Role_UserExample role_UserExample = new Role_UserExample();
        //2. 设置total属性
        List<Role_User> role_UserListDB = role_UserMapper.selectByExample(role_UserExample);
        PageInfo<Role_User> pageInfo = new PageInfo<>(role_UserListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<Role_UserDto> role_UserDtoList = CopyUtil.copyList(role_UserListDB, Role_UserDto.class);
        pageDto.setList(role_UserDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(Role_UserDto role_UserDto) {
        // Role_User role_User = new Role_User();
        // BeanUtils.copyProperties(role_UserDto,role_User);
        //工具类转换
        Role_User role_User = CopyUtil.copy(role_UserDto, Role_User.class);
        if (StringUtils.isEmpty(role_UserDto.getId())){
            //插入
            this.insert(role_User);
        }else{
            this.update(role_User);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        role_UserMapper.deleteByPrimaryKey(id);
    }

    private void insert(Role_User role_User) {
        //设定新增的id的uuid值
        role_User.setId(UuidUtil.getShortUuid());
        role_UserMapper.insert(role_User);
    }

    private void update(Role_User role_User) {
        role_UserMapper.updateByPrimaryKey(role_User);
    }
    
}    