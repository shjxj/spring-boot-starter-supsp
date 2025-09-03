package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.helper.AuthCommon;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class AuthIntercepter implements HandlerInterceptor {

    @Resource
    private AuthIntercepterContext authIntercepterContext;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        AuthMemberType authMemberType = AuthCommon.authMemberType(request);
        authIntercepterContext.preHandle(
                authMemberType,
                request,
                response,
                handler
        );
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        AuthMemberType authMemberType = AuthCommon.authMemberType(request);
        authIntercepterContext.postHandle(
                authMemberType,
                request,
                response,
                handler,
                modelAndView
        );
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        AuthMemberType authMemberType = AuthCommon.authMemberType(request);
        authIntercepterContext.afterCompletion(
                authMemberType,
                request,
                response,
                handler,
                ex
        );
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
