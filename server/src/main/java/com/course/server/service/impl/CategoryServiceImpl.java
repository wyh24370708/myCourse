package com.course.server.service.impl;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.service.CategoryService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 分类结构 查询所有
     * @return
     */
    @Override
    public List<CategoryDto> all() {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort desc");
        List<Category> categoryListDB = categoryMapper.selectByExample(categoryExample);
        //工具类转换
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryListDB, CategoryDto.class);
        return categoryDtoList;
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
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        //2. 设置total属性
        List<Category> categoryListDB = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryListDB, CategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(CategoryDto categoryDto) {
        // Category category = new Category();
        // BeanUtils.copyProperties(categoryDto,category);
        //工具类转换
        Category category = CopyUtil.copy(categoryDto, Category.class);
        if (StringUtils.isEmpty(categoryDto.getId())){
            //插入
            this.insert(category);
        }else{
            this.update(category);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    private void insert(Category category) {
        //设定新增的id的uuid值
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
    
}    