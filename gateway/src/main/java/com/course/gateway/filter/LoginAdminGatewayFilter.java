package com.course.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 请求地址中不包含/admin/的，不是控台请求，不需要拦截
        if (!path.contains("/admin")){
            LOG.info("非admin控台登录验证：{}", path);
            return chain.filter(exchange);
        }
        //登陆,退出,验证码不拦截
        if (path.contains("/system/admin/user/login")
            || path.contains("/system/admin/user/logout")
            || path.contains("/system/admin/imageCode/checkCode/")){

            LOG.info("不需要控台登录验证：{}", path);
            return chain.filter(exchange);
        }

        //获取header的token参数   token为null拦截
        String token = exchange.getRequest().getHeaders().getFirst("token");
        LOG.info("控台登陆验证开始:token={}",token);
        if(StringUtils.isEmpty(token) || token == null){
            LOG.info("token=null,请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);//401未授权
            return exchange.getResponse().setComplete();
        }
        Object loginUser = redisTemplate.opsForValue().get(token);
        if (loginUser == null){//拦截
            LOG.info("token无效,请求被拦截");
            //token过期,无法查询数据,不需要报错,应该跳转到login页面,
            HttpHeaders headers = exchange.getResponse().getHeaders();
            headers.set("Access-Control-Allow-Origin", exchange.getRequest().getHeaders().getOrigin());
            headers.set("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
            headers.set("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
            headers.set("Access-Control-Allow-Credentials","true");
            JSONObject message = new JSONObject();
            message.put("status",401);
            message.put("msg", "error");
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            ServerHttpResponse response = exchange.getResponse();
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }else{//放行
            LOG.info("登陆成功:{}",loginUser);
            return chain.filter(exchange);
        }
    }



    @Override
    public int getOrder() {
        return 1;
    }
}
