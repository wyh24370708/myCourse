package com.course.server.service.impl;

import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.${Domain}Mapper;
import com.course.server.service.${Domain}Service;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ${Domain}ServiceImpl implements ${Domain}Service {

    @Resource
    private ${Domain}Mapper ${domain}Mapper;

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
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("course_id desc");
        //2. 设置total属性
        List<${Domain}> ${domain}ListDB = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}ListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}ListDB, ${Domain}Dto.class);
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(${Domain}Dto ${domain}Dto) {
        // ${Domain} ${domain} = new ${Domain}();
        // BeanUtils.copyProperties(${domain}Dto,${domain});
        //工具类转换
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isEmpty(${domain}Dto.getId())){
            //插入
            this.insert(${domain});
        }else{
            this.update(${domain});
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

    private void insert(${Domain} ${domain}) {
        //设定新增的id的uuid值
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }

    private void update(${Domain} ${domain}) {
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }
    
}    