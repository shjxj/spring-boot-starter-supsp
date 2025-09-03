package com.supsp.springboot.core.config;

import com.supsp.springboot.core.auth.AuthIntercepter;
import com.supsp.springboot.core.config.intercepter.ApiIdempotentCheck;
import com.supsp.springboot.core.config.intercepter.SensitiveInterceptor;
import com.supsp.springboot.core.config.intercepter.WebInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
@EnableDiscoveryClient
public class WebConfigurer implements WebMvcConfigurer {

    @Resource
    private WebInterceptor webInterceptor;

    @Resource
    private SensitiveInterceptor sensitiveInterceptor;

    @Resource
    private AuthIntercepter authIntercepter;

    @Resource
    private ApiIdempotentCheck apiIdempotentCheck;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // order 值越小 优先级越高
        registry.addInterceptor(webInterceptor)
                .order(9);
        registry.addInterceptor(sensitiveInterceptor)
                .order(19);

        registry.addInterceptor(authIntercepter)
                .order(9999);

        // 最后
        registry.addInterceptor(apiIdempotentCheck)
                .order(999999);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
