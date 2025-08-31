package com.supsp.springboot.core.helper;

import com.supsp.springboot.core.utils.HttpServletContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpServletContext {

    private static HttpServletContextUtil httpServletContextUtil;

    public HttpServletContext(HttpServletContextUtil httpServletContextUtil) {
        HttpServletContext.httpServletContextUtil = httpServletContextUtil;
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = httpServletContextUtil.getRequest();
        if (ObjectUtils.isNotEmpty(request)) {
            return request;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isNotEmpty(requestAttributes)) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = httpServletContextUtil.getResponse();
        if (ObjectUtils.isNotEmpty(response)) {
            return response;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isNotEmpty(requestAttributes)) {
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        return null;
    }
}
