package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.utils.StrUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Component
@Slf4j
public class AuthIntercepterContext {

    private final Map<String, IAuthIntercepterService> authIntercepterServiceMap;

    public AuthIntercepterContext(
            Map<String, IAuthIntercepterService> authIntercepterServiceMap
    ) {
        this.authIntercepterServiceMap = authIntercepterServiceMap;
    }

    private String getServiceName(String memberType) {
        if (StrUtils.isBlank(memberType)) {
            return null;
        }
        return memberType.trim().toLowerCase() + "AuthIntercepter";
    }

    private String getServiceName(AuthMemberType memberType) {
        if (memberType == null) {
            return null;
        }
        return getServiceName(memberType.getCode());
    }

    public IAuthIntercepterService getService(AuthMemberType memberType) {
        if (authIntercepterServiceMap.isEmpty()) {
            return null;
        }
        String serviceName = getServiceName(memberType);
        if (StrUtils.isBlank(serviceName) || !authIntercepterServiceMap.containsKey(serviceName)) {
            return null;
        }
        return authIntercepterServiceMap.get(serviceName);
    }

    public void preHandle(
            AuthMemberType memberType,
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        if (memberType == null) {
            return;
        }
        IAuthIntercepterService intercepterService = getService(memberType);
        if (intercepterService == null) {
            return;
        }
        intercepterService.preHandle(
                request,
                response,
                handler
        );
    }

    public void postHandle(
            AuthMemberType memberType,
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        if (memberType == null) {
            return;
        }
        IAuthIntercepterService intercepterService = getService(memberType);
        if (intercepterService == null) {
            return;
        }
        intercepterService.postHandle(
                request,
                response,
                handler,
                modelAndView
        );
    }

    public void afterCompletion(
            AuthMemberType memberType,
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        if (memberType == null) {
            return;
        }
        IAuthIntercepterService intercepterService = getService(memberType);
        if (intercepterService == null) {
            return;
        }
        intercepterService.afterCompletion(
                request,
                response,
                handler,
                ex
        );
    }
}
