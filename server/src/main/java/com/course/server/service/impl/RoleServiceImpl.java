package com.course.server.service.impl;

import com.course.server.domain.Role;
import com.course.server.domain.RoleExample;
import com.course.server.domain.Role_resource;
import com.course.server.domain.Role_resourceExample;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.Role_resourceMapper;
import com.course.server.service.RoleService;
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

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private Role_resourceMapper role_resourceMapper;

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
        RoleExample roleExample = new RoleExample();
        //2. 设置total属性
        List<Role> roleListDB = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roleListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<RoleDto> roleDtoList = CopyUtil.copyList(roleListDB, RoleDto.class);
        pageDto.setList(roleDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(RoleDto roleDto) {
        // Role role = new Role();
        // BeanUtils.copyProperties(roleDto,role);
        //工具类转换
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (StringUtils.isEmpty(roleDto.getId())){
            //插入
            this.insert(role);
        }else{
            this.update(role);
        }
    }

    /**
     * [批量]保存角色资源
     * @param roleDto
     */
    @Override
    @Transactional
    public void saveResource(RoleDto roleDto) {
        LOG.info("批量保存角色资源开始:{}",roleDto);
        //先清空,在保存
        String roleId = roleDto.getId();
        Role_resourceExample example = new Role_resourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        role_resourceMapper.deleteByExample(example);
        //保存角色资源
        List<String> resourceIds = roleDto.getResourceIds();
        for (String resourceId : resourceIds) {
            Role_resource roleResource = new Role_resource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            role_resourceMapper.insert(roleResource);
        }
        LOG.info("批量保存角色资源结束");
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    private void insert(Role role) {
        //设定新增的id的uuid值
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }

    private void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }
    
}    