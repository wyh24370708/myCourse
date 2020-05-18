package com.course.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        LOG.info("gateway模块 启动了! ! !");
        LOG.info("gateway地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

    /**
     * gateway中解决跨域问题
     * 过滤器
     * @return
     */
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowCredentials(Boolean.TRUE);// 允许cookies跨域
        corsConfig.addAllowedHeader("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        corsConfig.addAllowedMethod("*");// 允许提交请求的方法类型，*表示全部允许
        corsConfig.addAllowedOrigin("*");// #允许访问的头信息,*表示全部
        corsConfig.setMaxAge(3600L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsConfig);

        CorsWebFilter corsWebFilter = new CorsWebFilter(source);
        return corsWebFilter;
    }


}
