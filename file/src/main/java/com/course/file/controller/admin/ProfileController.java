package com.course.file.controller.admin;

import com.course.server.dto.ProfileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ProfileService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @GetMapping  get请求
 * @PostMapping post请求
 * @DeleteMapping delete的请求
 */
@RestController
@RequestMapping("/admin/profile")
public class ProfileController {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);
    public static final String BUSINESS_NAME = "业务文件";

    @Resource
    private ProfileService profileService;

    /**
     * 查询所有
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto findAll(@RequestBody PageDto pageDto) {
        //统一返回格式 start
        ResponseDto<PageDto> responseDto = new ResponseDto<PageDto>();

        //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
        profileService.findAll(pageDto);
        LOG.info("查询业务文件结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param profileDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ProfileDto profileDto) {
        //统一返回格式 start
        ResponseDto<ProfileDto> responseDto = new ResponseDto<ProfileDto>();

        //保存校验
        ValidatorUtil.length(profileDto.getName(), "名称", 1, 100);
        ValidatorUtil.require(profileDto.getPath(), "相对路径");
        ValidatorUtil.length(profileDto.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(profileDto.getSuffix(), "后缀", 1, 10);

        profileService.save(profileDto);
        LOG.info("保存业务文件结果:{}",profileDto);

        //统一返回格式 end
        responseDto.setContent(profileDto);
        return responseDto;
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();
        profileService.delete(id);
        LOG.info("删除业务文件的id:{}",id);
        return responseDto;
    }

}
