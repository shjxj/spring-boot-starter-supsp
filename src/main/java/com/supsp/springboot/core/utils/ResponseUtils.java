package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.base.Result;
import com.supsp.springboot.core.exceptions.ExceptionCodes;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
@Slf4j
public class ResponseUtils {

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getResponse() : null;
    }

    public static void responseUnauthorizedHeader(HttpServletResponse response) {
        if (response == null) {
            return;
        }
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    public static void responseUnauthorizedHeader(ServletResponse response) {
        try {
            responseUnauthorizedHeader((HttpServletResponse) response);
        } catch (Exception e) {
            log.error("responseUnauthorizedHeader exception", e);
        }
    }

    public static void responseUnauthorizedContent(HttpServletResponse response) {
        try {
            response.getWriter().write(
                    JsonUtil.toJSONString(
                            Result.fail(
                                    ExceptionCodes.SIGN_ERROR.getCode(), ExceptionCodes.NOT_LOGIN_IN.getMessage()
                            )
                    )
            );
        } catch (IOException e) {
            log.error("responseUnauthorizedContent exception", e);
        }
    }

    public static void responseUnauthorizedContent(ServletResponse response) {
        try {
            responseUnauthorizedContent((HttpServletResponse) response);
        } catch (Exception e) {
            log.error("responseUnauthorizedContent exception", e);
        }
    }

    public static void responseUnauthorized(HttpServletResponse response) {
        responseUnauthorizedHeader(response);
        responseUnauthorizedContent(response);
    }

    public static void responseUnauthorized(ServletResponse response) {
        try {
            responseUnauthorized((HttpServletResponse) response);
        } catch (Exception e) {
            log.error("responseUnauthorized exception", e);
        }
    }

    public static void responseContentFlush(HttpServletResponse response) {
        try {
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("responseUnauthorizedContent exception", e);
        }
    }

    public static void responseContentFlush(ServletResponse response) {
        try {
            responseContentFlush((HttpServletResponse) response);
        } catch (Exception e) {
            log.error("responseUnauthorizedContent exception", e);
        }
    }

    public static void responseUnauthorizedFlush(HttpServletResponse response) {
        responseUnauthorizedHeader(response);
        responseUnauthorizedContent(response);
        responseContentFlush(response);
    }

    public static void responseUnauthorizedFlush(ServletResponse response) {
        try {
            responseUnauthorizedFlush((HttpServletResponse) response);
        } catch (Exception e) {
            log.error("responseUnauthorized exception", e);
        }
    }

    public static void responseStatusOK(HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("responseStatusOK exception", e);
        }
    }

    public static void responseStatusOK(ServletResponse response) {
        try {
            responseStatusOK((HttpServletResponse) response);
        } catch (Exception e) {
            log.error("responseStatusOK exception", e);
        }
    }

}
