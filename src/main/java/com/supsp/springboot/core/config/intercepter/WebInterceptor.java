package com.supsp.springboot.core.config.intercepter;

import com.supsp.springboot.core.threads.CacheTables;
import com.supsp.springboot.core.threads.GlobalData;
import com.supsp.springboot.core.threads.SensitiveContext;
import com.supsp.springboot.core.threads.ThreadData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        // log.debug("WebIntercepter postHandle;");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        // data remove after
        CacheTables.remove();
        ThreadData.remove();
        GlobalData.remove();
        SensitiveContext.remove();

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
