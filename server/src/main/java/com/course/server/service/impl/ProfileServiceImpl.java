package com.course.server.service.impl;

import com.course.server.domain.Profile;
import com.course.server.domain.ProfileExample;
import com.course.server.dto.ProfileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ProfileMapper;
import com.course.server.service.ProfileService;
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
public class ProfileServiceImpl implements ProfileService {

    @Resource
    private ProfileMapper profileMapper;

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
        ProfileExample profileExample = new ProfileExample();
        //2. 设置total属性
        List<Profile> profileListDB = profileMapper.selectByExample(profileExample);
        PageInfo<Profile> pageInfo = new PageInfo<>(profileListDB);
        pageDto.setTotalNum(pageInfo.getTotal());
        
        //工具类转换
        List<ProfileDto> profileDtoList = CopyUtil.copyList(profileListDB, ProfileDto.class);
        pageDto.setList(profileDtoList);
    }

    /**
     * 保存
     *
     * 新增和修改
     */
    @Override
    public void save(ProfileDto profileDto) {
        // Profile profile = new Profile();
        // BeanUtils.copyProperties(profileDto,profile);
        //工具类转换
        Profile profile = CopyUtil.copy(profileDto, Profile.class);
        if (StringUtils.isEmpty(profileDto.getId())){
            //插入
            this.insert(profile);
        }else{
            this.update(profile);
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        profileMapper.deleteByPrimaryKey(id);
    }

    private void insert(Profile profile) {
        Date date = new Date();
        profile.setCreatedAt(date);
        //设定新增的id的uuid值
        profile.setId(UuidUtil.getShortUuid());
        profileMapper.insert(profile);
    }

    private void update(Profile profile) {
        Date date = new Date();
        profile.setUpdatedAt(date);
        profileMapper.updateByPrimaryKey(profile);
    }
    
}    