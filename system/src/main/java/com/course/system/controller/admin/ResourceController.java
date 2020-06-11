package com.course.system.controller.admin;

import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ResourceService;
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
@RequestMapping("/admin/resource")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);
    public static final String BUSINESS_NAME = "资源";

    @Resource
    private ResourceService resourceService;

    /**
     * 查询所有
     * @param pageDto
     * @return
     */
    @PostMapping("/all")
    public ResponseDto findAll() {
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();
        //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
        List<ResourceDto> resourceDtoList = resourceService.findAll();
        LOG.info("查询资源结果:{}",resourceDtoList);
        //统一返回格式 end
        responseDto.setContent(resourceDtoList);
        return responseDto;
    }

    /**
     * 保存资源json
     * @param resource_json
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody String resource_json) {
        //统一返回格式 start
        ResponseDto responseDto = new ResponseDto();

        //保存校验
        ValidatorUtil.require(resource_json,"资源");

        resourceService.saveJson(resource_json);
        LOG.info("保存资源结果:{}",resource_json);

        //统一返回格式 end
        responseDto.setContent(resource_json);
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
        resourceService.delete(id);
        LOG.info("删除资源的id:{}",id);
        return responseDto;
    }

}
