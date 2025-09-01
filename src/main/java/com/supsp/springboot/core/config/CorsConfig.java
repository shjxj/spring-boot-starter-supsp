package com.supsp.springboot.core.config;

import com.supsp.springboot.core.consts.Constants;
import jakarta.annotation.Resource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Resource
    private CoreProperties coreProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 增加 公开 headers
    private void addExposedHeaders(CorsConfiguration config) {
        config.addExposedHeader(CoreProperties.headerName("System-Name"));
//        config.addExposedHeader(CoreProperties.appUidHeaderName());

        // admin
        config.addExposedHeader(CoreProperties.tokenAdminHeaderName());
        config.addExposedHeader(CoreProperties.tokenAdminOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenAdminDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenAdminShopHeaderName());

        // tenant
        config.addExposedHeader(CoreProperties.tokenTenantHeaderName());
        config.addExposedHeader(CoreProperties.tokenTenantOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenTenantDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenTenantShopHeaderName());

        // merchant
        config.addExposedHeader(CoreProperties.tokenMerchantHeaderName());
        config.addExposedHeader(CoreProperties.tokenMerchantOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenMerchantDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenMerchantShopHeaderName());

        // consumer
        config.addExposedHeader(CoreProperties.tokenConsumerHeaderName());
        config.addExposedHeader(CoreProperties.tokenConsumerOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenConsumerDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenConsumerShopHeaderName());

        // api
        config.addExposedHeader(CoreProperties.tokenApiHeaderName());
        config.addExposedHeader(CoreProperties.tokenApiOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenApiDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenApiShopHeaderName());


        config.addExposedHeader(CoreProperties.headerName(Constants.CAPTCHA_HEADER));
    }

    private void addAllowedHeader(CorsConfiguration config) {
        config.addExposedHeader(CoreProperties.headerName("System-Name"));
//        config.addExposedHeader(CoreProperties.appUidHeaderName());

        // admin
        config.addExposedHeader(CoreProperties.tokenAdminHeaderName());
        config.addExposedHeader(CoreProperties.tokenAdminOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenAdminDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenAdminShopHeaderName());

        // tenant
        config.addExposedHeader(CoreProperties.tokenTenantHeaderName());
        config.addExposedHeader(CoreProperties.tokenTenantOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenTenantDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenTenantShopHeaderName());

        // merchant
        config.addExposedHeader(CoreProperties.tokenMerchantHeaderName());
        config.addExposedHeader(CoreProperties.tokenMerchantOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenMerchantDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenMerchantShopHeaderName());

        // consumer
        config.addExposedHeader(CoreProperties.tokenConsumerHeaderName());
        config.addExposedHeader(CoreProperties.tokenConsumerOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenConsumerDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenConsumerShopHeaderName());

        // api
        config.addExposedHeader(CoreProperties.tokenApiHeaderName());
        config.addExposedHeader(CoreProperties.tokenApiOrgHeaderName());
        config.addExposedHeader(CoreProperties.tokenApiDepHeaderName());
        config.addExposedHeader(CoreProperties.tokenApiShopHeaderName());

        config.addExposedHeader(CoreProperties.headerName(Constants.CAPTCHA_HEADER));
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        addExposedHeaders(config);
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
