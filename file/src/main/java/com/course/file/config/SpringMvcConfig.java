package com.course.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    /**
     * 静态资源的配置
     * 路径对外暴露
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**")
                .addResourceLocations("file:D:/0-yayuanzi/upload_file/course");
    }
    //  D:/0-yayuanzi/upload_file/teacher/Yqfm2jaa-man.png//  d盘直接访问
    //  http://127.0.0.1:9003/file/f/course/teacher/ErBQHwrU-female.png  //路径对外暴露
    //  由于我们是做路由转发 ,  所以使用9000端口也是可以的
}
