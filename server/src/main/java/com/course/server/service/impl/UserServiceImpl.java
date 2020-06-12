package com.course.server.service.impl;

import com.course.server.domain.*;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.RoleDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.Role_UserMapper;
import com.course.server.mapper.UserMapper;
import com.course.server.service.RoleService;
import com.course.server.service.UserService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private Role_UserMapper role_userMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 【登录】
     */
    @Override
    public LoginUserDto login(UserDto userDto) {
        Boolean hasKey = redisTemplate.hasKey(userDto.getLoginName());
        if (hasKey){
            int count = (int) redisTemplate.opsForValue().get(userDto.getLoginName());
            if (count==5){
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_FREEZE.getDesc());
            }
        }
        User userDB = this.selectByLoginName(userDto.getLoginName());
        if(userDB!=null){
            if (userDto.getPassword().equals(userDB.getPassword())){
                LOG.info("登陆成功");
                if (hasKey){
                    redisTemplate.delete(userDto.getLoginName());
                }
                return CopyUtil.copy(userDB,LoginUserDto.class);
            }else{
                LOG.info("密码错误=>输入密码:{},数据库密码:{}", userDto.getPassword(), userDB.getPassword());
                //密码输入错误五次后,冻结账户
                freezeCheck(userDto);
                return null;
            }
        }else{
            LOG.info("用户名不存在:{}",userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_ERROR.getDesc());
        }
    }

    //密码次数校验
    private void freezeCheck(UserDto userDto) {
//        boolean flag = false;//账户冻结标志位
        int count;
        Boolean hasKey = redisTemplate.hasKey(userDto.getLoginName());
        if (!hasKey){
            count = 1;
            redisTemplate.opsForValue().set(userDto.getLoginName(),count,300, TimeUnit.SECONDS);
            throw new BusinessException(BusinessExceptionCode.LOGIN_ERROR.getDesc()+"_"+count);
        }else{
            count = (int) redisTemplate.opsForValue().get(userDto.getLoginName());
        }
        if (count>=5) {
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_FREEZE.getDesc());
        }
        if (count < 5){
            count = count + 1;
            redisTemplate.opsForValue().set(userDto.getLoginName(),count,300, TimeUnit.SECONDS);
            if(count==5){
                //账户冻结,设置冻结时间
                redisTemplate.opsForValue().set(userDto.getLoginName(),count,300, TimeUnit.SECONDS);
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_FREEZE.getDesc());
            }
            throw new BusinessException(BusinessExceptionCode.LOGIN_ERROR.getDesc()+"_"+count);
        }

    }

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
    public User selectByLoginName(String loginName) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(loginName);
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

    /**
     * 【基于用户查询角色(已分配和未分配)】
     */
    @Override
    public UserDto selectRole(String userId) {
        // 查询所有角色
        List<Role> roleList = roleMapper.selectByExample(null);
        //查询用户已经分配的角色
        Role_UserExample example = new Role_UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Role_User> role_userList = role_userMapper.selectByExample(example);
        //封装到userDto
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        List<RoleDto> grantedRoles = new ArrayList<>();//已分配
        List<RoleDto> ungrantedRoles = new ArrayList<>();//未分配
        //存入list
        for (Role role : roleList) {
            RoleDto roleDto = CopyUtil.copy(role, RoleDto.class);
            boolean flag= false;
            for (Role_User role_user : role_userList) {
                if (role.getId().equals(role_user.getRoleId())) {//表示已分配
                    grantedRoles.add(roleDto);
                    flag = true;
                    break;
                }
            }
            if (!flag){
                ungrantedRoles.add(roleDto);
            }

        }
        userDto.setGrantedRoles(grantedRoles);
        userDto.setUngrantedRoles(ungrantedRoles);
        LOG.info("分配角色信息:{}",userDto);
        return userDto;
    }

    /**
     * 【保存用户分配的角色】
     */
    @Override
    public void saveGrantedRole(UserDto userDto) {
        String userId = userDto.getId();
        //先清除
        Role_UserExample example = new Role_UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        role_userMapper.deleteByExample(example);
        //保存分配角色
        Role_User role_user = new Role_User();
        List<RoleDto> grantedRoles = userDto.getGrantedRoles();
        for (RoleDto roleDto : grantedRoles) {
            role_user.setId(UuidUtil.getShortUuid());
            role_user.setRoleId(roleDto.getId());
            role_user.setUserId(userId);
            role_userMapper.insert(role_user);
        }
    }

    private void insert(User user) {
        Date date = new Date();
        user.setCreatedAt(date);
        //设定新增的id的uuid值
        user.setId(UuidUtil.getShortUuid());
        User userDB = this.selectByLoginName(user.getLoginName());
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