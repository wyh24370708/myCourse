package com.course.server.service.impl;

import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.UserMapper;
import com.course.server.service.UserService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

    /**
     * 查询单个用户
     */
    @Override
    public User selectByLoginName(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(user.getLoginName());
        List<User> userListDB = userMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(userListDB)){
            return userListDB.get(0);
        }else{
            return null;
        }
    }

    /**
     * 【密码重置保存】
     */
    @Override
    public void savePwd(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId())
            .setPassword(userDto.getPassword())
            .setUpdatedAt(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    private void insert(User user) {
        Date date = new Date();
        user.setCreatedAt(date);
        //设定新增的id的uuid值
        user.setId(UuidUtil.getShortUuid());
        User userDB = this.selectByLoginName(user);
        if (userDB!=null){
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST.getDesc());
        }
        userMapper.insert(user);
    }

    private void update(User user) {
        Date date = new Date();
        user.setUpdatedAt(date);
        user.setPassword(null);
        userMapper.updateByPrimaryKeySelective(user);//对于空的字段值,更新不作处理,也可以查询出来重新赋同样的值
    }
    
}    