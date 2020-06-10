package com.course.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.course.server.dto.*;
import com.course.server.service.UserService;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @GetMapping  get请求
 * @PostMapping post请求
 * @DeleteMapping delete的请求
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    public static final String BUSINESS_NAME = "用户";

    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 【登录】
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request){
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        //登陆前,前判断验证码
        //      根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        //      前后端分离的项目中,每次的ajax请求,后端的sessionId是不一样的.main.js中存解决办法,前后端每次ajax是不同的sessionId
//        String sessionCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        //redis中获取imageCodeToken
        String imageCodeCache = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        LOG.info("redis中获取到验证码:{}",imageCodeCache);
        // 判断验证码
        if (StringUtils.isEmpty(imageCodeCache)){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
        }
        if (userDto.getImageCode()==null || !imageCodeCache.equals(userDto.getImageCode().trim().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码输入错误");
            LOG.info("用户登录失败，验证码输入错误");
            return responseDto;
        }else{
            //验证通过后，移除验证码
//            request.getSession().removeAttribute(userDto.getImageCodeToken());
            //从redis中移除验证码
            redisTemplate.delete(userDto.getImageCodeToken());
        }
        //登陆
        LoginUserDto loginUserDto = userService.login(userDto);
        //后端保存用户登陆信息  存储位置session转为redis
//        request.getSession().setAttribute(Constans.LOGIN_USER,loginUserDto);
        String token = UuidUtil.getShortUuid();
        loginUserDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto),300, TimeUnit.SECONDS);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    /**
     * 【退出登陆】
     */
    @PostMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable("token") String token){
        ResponseDto responseDto = new ResponseDto();
        //session中删除
//        request.getSession().removeAttribute(Constans.LOGIN_USER);
        //redis中删除
        redisTemplate.delete(token);
        LOG.info("退出登陆,redis中删除登陆信息:{}",token);
        return responseDto;
    }

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
        userService.findAll(pageDto);
        LOG.info("查询结果:{}",pageDto);
        //统一返回格式 end
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增
     * @param userDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody UserDto userDto) {
        //统一返回格式 start
        ResponseDto<UserDto> responseDto = new ResponseDto<UserDto>();
        //密码二次md5加密
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));

        //保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登录名");
        ValidatorUtil.length(userDto.getLoginName(), "登录名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.length(userDto.getPassword(), "密码", 1, 50);

        userService.save(userDto);
        LOG.info("保存结果:{}",userDto);

        //统一返回格式 end
        responseDto.setContent(userDto);
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
        userService.delete(id);
        LOG.info("删除的id:{}",id);
        return responseDto;
    }

    /**
     * 【密码重置保存】
     */
    @PostMapping("/savePwd")
    public ResponseDto savePwd(@RequestBody UserDto userDto){
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        userService.savePwd(userDto);
        return responseDto;
    }


}
