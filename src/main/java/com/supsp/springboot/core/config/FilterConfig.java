package com.supsp.springboot.core.config;

import com.supsp.springboot.core.config.filter.SystemInitFilter;
import com.supsp.springboot.core.config.filter.SystemLocalDataFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SystemInitFilter> initFilterRegistration() {
        FilterRegistrationBean<SystemInitFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SystemInitFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE + 1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<SystemLocalDataFilter> customFilterRegistration() {
        FilterRegistrationBean<SystemLocalDataFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SystemLocalDataFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE + 2);
        return registration;
    }
}
