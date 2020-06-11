package com.course.server.service.impl;

import com.course.server.domain.Resource;
import com.course.server.domain.ResourceExample;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.service.ResourceService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

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
        ResourceExample resourceExample = new ResourceExample();
        //2. 设置total属性
        List<Resource> resourceListDB = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceListDB, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(ResourceDto resourceDto) {
        // Resource resource = new Resource();
        // BeanUtils.copyProperties(resourceDto,resource);
        //工具类转换
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if (StringUtils.isEmpty(resourceDto.getId())){
            //插入
            this.insert(resource);
        }else{
            this.update(resource);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    private void insert(Resource resource) {
        //设定新增的id的uuid值
        resource.setId(UuidUtil.getShortUuid());
        resourceMapper.insert(resource);
    }

    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }
    
}    