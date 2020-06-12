package com.course.system.controller.admin;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleService;
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
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    public static final String BUSINESS_NAME = "";

    @Resource
    private RoleService roleService;

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
        roleService.findAll(pageDto);
        LOG.info("查询结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param roleDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleDto roleDto) {
        //统一返回格式 start
        ResponseDto<RoleDto> responseDto = new ResponseDto<RoleDto>();

        //保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);

        roleService.save(roleDto);
        LOG.info("保存结果:{}",roleDto);

        //统一返回格式 end
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 保存角色资源
     * @param roleDto
     * @return
     */
    @PostMapping("/saveResource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto){
        ResponseDto responseDto = new ResponseDto();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
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
        roleService.delete(id);
        LOG.info("删除的id:{}",id);
        return responseDto;
    }

}
