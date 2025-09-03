package com.supsp.springboot.core.config.intercepter;

import com.supsp.springboot.core.annotations.Sensitive;
import com.supsp.springboot.core.threads.SensitiveContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

@Component
@Slf4j
public class SensitiveInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
//        log.debug(
//                """
//                        \n■■■■■ Sensitive Interceptor
//                        """
//        );

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();
//        log.debug(
//                "\n■■■■■ Sensitive Interceptor\nclazz: {}\nmethod: {}\n",
//                clazz.getName(),
//                method.getName()
//        );

        Sensitive clazzSensitive = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Sensitive.class);
        Sensitive methodSensitive = handlerMethod.getMethodAnnotation(Sensitive.class);

        if (
                ObjectUtils.isNotEmpty(clazzSensitive)
        ) {
            if (ArrayUtils.isNotEmpty(clazzSensitive.enable())) {
                // SensitiveContext.set();
                for (Class<?> clz : clazzSensitive.enable()) {
                    if (clz == null) {
                        continue;
                    }
                    SensitiveContext.set(
                            clz,
                            true
                    );
                }
            }

            if (ArrayUtils.isNotEmpty(clazzSensitive.disable())) {
                // SensitiveContext.set();
                for (Class<?> clz : clazzSensitive.disable()) {
                    if (clz == null) {
                        continue;
                    }
                    SensitiveContext.set(
                            clz,
                            false
                    );
                }
            }
        }

        if (
                ObjectUtils.isNotEmpty(methodSensitive)
        ) {
            if (ArrayUtils.isNotEmpty(methodSensitive.enable())) {
                // SensitiveContext.set();
                for (Class<?> clz : methodSensitive.enable()) {
                    if (clz == null) {
                        continue;
                    }
                    SensitiveContext.set(
                            clz,
                            true
                    );
                }
            }

            if (ArrayUtils.isNotEmpty(methodSensitive.disable())) {
                // SensitiveContext.set();
                for (Class<?> clz : methodSensitive.disable()) {
                    if (clz == null) {
                        continue;
                    }
                    SensitiveContext.set(
                            clz,
                            false
                    );
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SensitiveContext.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
