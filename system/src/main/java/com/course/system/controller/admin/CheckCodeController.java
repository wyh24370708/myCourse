package com.course.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.course.server.service.UserService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/imageCode")
public class CheckCodeController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private HttpServletRequest request;
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/checkCode/{imageCodeToken}")
    public void getVerifyCodeImage(@PathVariable("imageCodeToken") String imageCodeToken,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {

        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 34,4);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        String imageCode = specCaptcha.text().toLowerCase();
        // 验证码存入session
//        request.getSession().setAttribute(imageCodeToken, JSON.toJSON(imageCode));
        //存放session中  改为 存放redis中
        redisTemplate.opsForValue().set(imageCodeToken, JSON.toJSON(imageCode),300, TimeUnit.SECONDS);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

}
