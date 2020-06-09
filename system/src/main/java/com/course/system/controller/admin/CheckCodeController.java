package com.course.system.controller.admin;

import com.course.server.service.UserService;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/admin/imageCode")
public class CheckCodeController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private HttpServletRequest request;
    @Resource
    private UserService userService;

    @GetMapping("/checkCode/{imageCodeToken}")
    public void getVerifyCodeImage(@PathVariable("imageCodeToken") String imageCodeToken,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        CaptchaUtil.out(100,34,4,request, response);
    }

}
