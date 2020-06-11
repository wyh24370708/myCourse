package com.course.system.controller.admin;

import com.course.server.dto.Role_resourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.Role_resourceService;
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
@RequestMapping("/admin/role_resource")
public class Role_resourceController {

    private static final Logger LOG = LoggerFactory.getLogger(Role_resourceController.class);
    public static final String BUSINESS_NAME = "角色资源关联";

    @Resource
    private Role_resourceService role_resourceService;

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
        role_resourceService.findAll(pageDto);
        LOG.info("查询角色资源关联结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param role_resourceDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody Role_resourceDto role_resourceDto) {
        //统一返回格式 start
        ResponseDto<Role_resourceDto> responseDto = new ResponseDto<Role_resourceDto>();

        //保存校验
        ValidatorUtil.require(role_resourceDto.getRoleId(), "角色id");
        ValidatorUtil.require(role_resourceDto.getResourceId(), "资源id");

        role_resourceService.save(role_resourceDto);
        LOG.info("保存角色资源关联结果:{}",role_resourceDto);

        //统一返回格式 end
        responseDto.setContent(role_resourceDto);
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
        role_resourceService.delete(id);
        LOG.info("删除角色资源关联的id:{}",id);
        return responseDto;
    }

}
