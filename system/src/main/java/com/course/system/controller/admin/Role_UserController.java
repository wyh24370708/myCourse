package com.course.system.controller.admin;

import com.course.server.dto.Role_UserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.Role_UserService;
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
@RequestMapping("/admin/role_User")
public class Role_UserController {

    private static final Logger LOG = LoggerFactory.getLogger(Role_UserController.class);
    public static final String BUSINESS_NAME = "角色用户关联";

    @Resource
    private Role_UserService role_UserService;

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
        role_UserService.findAll(pageDto);
        LOG.info("查询角色用户关联结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param role_UserDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody Role_UserDto role_UserDto) {
        //统一返回格式 start
        ResponseDto<Role_UserDto> responseDto = new ResponseDto<Role_UserDto>();

        //保存校验
        ValidatorUtil.require(role_UserDto.getRoleId(), "角色id");
        ValidatorUtil.require(role_UserDto.getUserId(), "用户id");

        role_UserService.save(role_UserDto);
        LOG.info("保存角色用户关联结果:{}",role_UserDto);

        //统一返回格式 end
        responseDto.setContent(role_UserDto);
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
        role_UserService.delete(id);
        LOG.info("删除角色用户关联的id:{}",id);
        return responseDto;
    }

}
