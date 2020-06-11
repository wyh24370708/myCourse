package com.course.server.service.impl;

import com.course.server.domain.Role_resource;
import com.course.server.domain.Role_resourceExample;
import com.course.server.dto.Role_resourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.Role_resourceMapper;
import com.course.server.service.Role_resourceService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Role_resourceServiceImpl implements Role_resourceService {

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
        Role_resourceExample role_resourceExample = new Role_resourceExample();
        //2. 设置total属性
        List<Role_resource> role_resourceListDB = role_resourceMapper.selectByExample(role_resourceExample);
        PageInfo<Role_resource> pageInfo = new PageInfo<>(role_resourceListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<Role_resourceDto> role_resourceDtoList = CopyUtil.copyList(role_resourceListDB, Role_resourceDto.class);
        pageDto.setList(role_resourceDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(Role_resourceDto role_resourceDto) {
        // Role_resource role_resource = new Role_resource();
        // BeanUtils.copyProperties(role_resourceDto,role_resource);
        //工具类转换
        Role_resource role_resource = CopyUtil.copy(role_resourceDto, Role_resource.class);
        if (StringUtils.isEmpty(role_resourceDto.getId())){
            //插入
            this.insert(role_resource);
        }else{
            this.update(role_resource);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        role_resourceMapper.deleteByPrimaryKey(id);
    }

    private void insert(Role_resource role_resource) {
        //设定新增的id的uuid值
        role_resource.setId(UuidUtil.getShortUuid());
        role_resourceMapper.insert(role_resource);
    }

    private void update(Role_resource role_resource) {
        role_resourceMapper.updateByPrimaryKey(role_resource);
    }
    
}    