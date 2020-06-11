package com.course.server.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<ResourceDto> findAll() {
        //查询数据库
        ResourceExample resourceExample = new ResourceExample();
        //2. 设置total属性
        List<Resource> resourceListDB = resourceMapper.selectByExample(resourceExample);
        //工具类转换
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceListDB, ResourceDto.class);
        return resourceDtoList;
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

    private void insert(Resource resource) {
        resourceMapper.insert(resource);
    }

    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存资源json树
     */
    @Override
    public void saveJson(String resource_json) {
        //解析接送数组为list
        List<ResourceDto> jsonList = JSON.parseArray(resource_json, ResourceDto.class);
        //创建一个新的list
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)){
            for (ResourceDto dto : jsonList) {
                dto.setParent("");
                add(list,dto);
            }
        }
        LOG.info("共{}条", list.size());
        resourceMapper.deleteByExample(null);//全部删除
        for (int i = 0, LEN = list.size(); i < LEN; i++){
            Resource resource = CopyUtil.copy(list.get(i), Resource.class);
            resourceMapper.insert(resource);
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    private void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if(!CollectionUtils.isEmpty(dto.getChildren())){
            for (ResourceDto childDto : dto.getChildren()) {
                childDto.setParent(dto.getId());
                add(list,childDto);
            }
        }
    }

}