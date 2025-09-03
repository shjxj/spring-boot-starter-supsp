package com.supsp.springboot.core.config.intercepter;

import com.supsp.springboot.core.annotations.ApiIdempotent;
import com.supsp.springboot.core.base.Result;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.enums.IgnoreValue;
import com.supsp.springboot.core.enums.SysModule;
import com.supsp.springboot.core.helper.AuthCommon;
import com.supsp.springboot.core.helper.SystemCommon;
import com.supsp.springboot.core.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.time.Duration;

@Component
@Slf4j
public class ApiIdempotentCheck implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
//        log.debug(
//                """
//                        \n■■■■■ Idempotent Interceptor
//                        """
//        );

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();
//        log.debug(
//                "\n■■■■■ ApiIdempotentCheck\nclazz: {}\n",
//                clazz.getName()
//        );
        ApiIdempotent clazzApiIdempotent = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ApiIdempotent.class);
        ApiIdempotent methodApiIdempotent = handlerMethod.getMethodAnnotation(ApiIdempotent.class);

        //
        if (ObjectUtils.isEmpty(clazzApiIdempotent) && ObjectUtils.isEmpty(methodApiIdempotent)){
            return true;
        }
        // 未设置则为不忽略
        IgnoreValue check = IgnoreValue.False;
        if (
                ObjectUtils.isNotEmpty(clazzApiIdempotent)
                        || ObjectUtils.isNotEmpty(methodApiIdempotent)
        ) {
            check = getIgnoreValue(clazzApiIdempotent, methodApiIdempotent);
        }

        if (check.equals(IgnoreValue.False)) {
            if (
                    !checkToken(
                            request,
                            handlerMethod.getBeanType(),
                            method,
                            clazzApiIdempotent,
                            methodApiIdempotent
                    )
            ) {
                failure(response);
                return false;
            }
        }

        return true;
    }

    //
    private static IgnoreValue getIgnoreValue(ApiIdempotent clazzApiIdempotent, ApiIdempotent methodApiIdempotent) {
        IgnoreValue check = IgnoreValue.False;
        if (
                clazzApiIdempotent != null
                        && clazzApiIdempotent.ignore() != null
                        && !clazzApiIdempotent.ignore().equals(IgnoreValue.Null)
        ) {
            check = clazzApiIdempotent.ignore();
        }

        if (
                methodApiIdempotent != null
                        && methodApiIdempotent.ignore() != null
                        && !methodApiIdempotent.ignore().equals(IgnoreValue.Null)
        ) {
            check = methodApiIdempotent.ignore();
        }
        return check;
    }

    private void failure(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        Result<Boolean> result = Result.fail("请勿重复提交");
        response.getWriter().write(JsonUtil.toJSONString(result));
    }

    private boolean checkToken(
            HttpServletRequest request,
            Class<?> clazz,
            Method method,
            ApiIdempotent clazzApiIdempotent,
            ApiIdempotent methodApiIdempotent
    ) {
        String ip = AuthCommon.requestIp();
        String sid = null;
        SysModule appApi = SystemCommon.getModule();
        switch (appApi) {
            case admin -> {
                sid = AuthCommon.adminAuthHeader();
            }
            case tenant -> {
                sid = AuthCommon.tenantAuthHeader();
            }
            case merchant -> {
                sid = AuthCommon.merchantAuthHeader();
            }
            case consumer -> {
                sid = AuthCommon.consumerAuthHeader();
            }
            case api -> {
                sid = AuthCommon.apiAuthHeader();
            }
            case null, default -> {
                sid = "";
            }
        }

        String userAgent = request.getHeader("User-Agent");
        String idempotentToken = CoreProperties.IDEMPOTENT_NAME
                + "::"
                + request.getContentType() + "-" +
                request.getMethod() + "-" +
                request.getContextPath() + "-" +
                request.getRequestURI() + "-" +
                clazz.getName() + "-" +
                method.getName() + "-" +
                ip + "-" +
                sid + "-" +
                userAgent;


        String idempotentKey = CryptUtils.sha256(idempotentToken);

        if (StrUtils.isBlank(idempotentKey)) {
            return true;
        }

        Long idempotentExpires = Constants.LONG_ZERO;
        if (
                ObjectUtils.isNotEmpty(clazzApiIdempotent)
                        || ObjectUtils.isNotEmpty(methodApiIdempotent)
        ) {
            if (clazzApiIdempotent != null && NumUtils.gtZero(clazzApiIdempotent.expires())) {
                idempotentExpires = clazzApiIdempotent.expires();
            }
            if (methodApiIdempotent != null && NumUtils.gtZero(methodApiIdempotent.expires())) {
                idempotentExpires = methodApiIdempotent.expires();
            }
        }
        // 未设置情况下二次请求超时时间
        if (
                ObjectUtils.isEmpty(clazzApiIdempotent)
                        && ObjectUtils.isEmpty(methodApiIdempotent)
        ) {
            idempotentExpires = 1L;

            // 如果为 POST 请求
            if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
                idempotentExpires = 3L;
            }
        }

        if (NumUtils.leZero(idempotentExpires)) {
            idempotentExpires = CoreProperties.IDEMPOTENT_EXPIRES;
        }
        if (NumUtils.leZero(idempotentExpires)) {
            idempotentExpires = Constants.DEFAULT_IDEMPOTENT_SECONDS;
        }
//        log.debug(
//                "\n■■■■■ ApiIdempotentCheck\nidempotentToken: {}\nidempotentKey: {}\nidempotentExpires: {}\n",
//                idempotentToken,
//                idempotentKey,
//                idempotentExpires
//        );
        return RedisUtils.setNx(
                idempotentKey,
                CommonUtils.timestamp(),
                Duration.ofSeconds(idempotentExpires)
        );
    }
}
