package com.course.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    //属性注入
    @Value("${file_up_path}")
    private String FILE_UP_PATH;
    /**
     * 静态资源的配置
     * 路径对外暴露
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**")
                .addResourceLocations("file:" + FILE_UP_PATH);
    }
    //  D:/0-yayuanzi/upload_file/teacher/Yqfm2jaa-man.png//  d盘直接访问
    //  http://127.0.0.1:9003/file/f//teacher/ErBQHwrU-female.png  //路径对外暴露
    //  由于我们是做路由转发 ,  所以使用9000端口也是可以的

    //  访问"/f/"路径后面的资源,用file:D:/0-yayuanzi/upload_file/course/代替,   最后的/和都要写上,前后相互对应
}
