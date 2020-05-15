package com.course.system;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableEurekaClient
//service分子模块后,需要使用这个注解扫描其他模块,自身模块的内容,在@SpringBootApplication已经扫描了
@ComponentScan("com.course")
@MapperScan("com.course.server.dao")
public class SystemApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SystemApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SystemApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        LOG.info("system模块 启动了! ! !");
        LOG.info("system地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}
