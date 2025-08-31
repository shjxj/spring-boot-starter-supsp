package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.consts.DataKeys;
import com.supsp.springboot.core.threads.GlobalData;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class RequestUtils {

    public static String servletPath(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        try {
            return request.getServletPath();
        }catch (Exception e){
            //
        }
        return null;
    }

    public static String[] servletPathArray(HttpServletRequest request) {
        String[] res = new String[0];
        String servletPath = servletPath(request);
        if (StrUtils.isBlank(servletPath)) {
            return res;
        }
        return StrUtils.isNotBlank(servletPath) ? StringUtils.split(servletPath, "/") : new String[0];
    }

    public static String pathModule(HttpServletRequest request) {
        String[] paths = servletPathArray(request);
        if (paths == null || paths.length < 2) {
            return null;
        }
        return ArrayUtils.get(paths, 0);
    }

    public static String pathApp(HttpServletRequest request) {
        String[] paths = servletPathArray(request);
        if (paths == null || paths.length < 3) {
            return null;
        }
        return ArrayUtils.get(paths, 1);
    }

    public static List<String> requestHeaderNames(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> names = request.getHeaderNames();

        List<String> headerNames = new ArrayList<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headerNames.add(name);
        }
        return headerNames;
    }

    public static List<String> getHeaderNames(@Nullable HttpServletRequest request) {
        List<String> headerNames = GlobalData.get(DataKeys.REQUEST_HEADER_NAMES);
        if (CommonUtils.isNotEmpty(headerNames)) {
            return headerNames;
        }
        return requestHeaderNames(request);
    }

    public static Map<String, String> requestHeaders(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        List<String> names = getHeaderNames(request);
        if (names == null || CommonUtils.isEmpty(names)) {
            return null;
        }

        Map<String, String> headers = new HashMap<>();
        for (String name : names) {
            headers.put(name.trim().toLowerCase(), request.getHeader(name));
        }
        return headers;
    }

    public static Map<String, String> getHeaders(@Nullable HttpServletRequest request) {
        Map<String, String> headers = GlobalData.get(DataKeys.REQUEST_HEADERS);
        if (CommonUtils.isNotEmpty(headers)) {
            return headers;
        }
        return requestHeaders(request);
    }

    public static String getHeader(String name, @Nullable HttpServletRequest request) {
        if (StrUtils.isBlank(name)) {
            return null;
        }
        Map<String, String> headers = getHeaders(request);
        if (
                headers == null
                        || CommonUtils.isEmpty(headers)
                        || !headers.containsKey(name.trim().toLowerCase())
        ) {
            return null;
        }
        return headers.get(name.trim().toLowerCase());
    }

    public static String getHeader(List<String> names, @Nullable HttpServletRequest request) {
        if (CommonUtils.isEmpty(names)) {
            return null;
        }
        Map<String, String> headers = getHeaders(request);
        if (CommonUtils.isEmpty(headers)) {
            return null;
        }
        for (String name : names) {
            if (
                    StrUtils.isBlank(name)
                            || !headers.containsKey(name.trim().toLowerCase())
            ) {
                continue;
            }
            return headers.get(name.trim().toLowerCase());
        }
        return null;
    }

    public static String getRemoteAddr(@Nullable HttpServletRequest request) {
        String remoteAddr = GlobalData.get(DataKeys.REQUEST_REMOTE_ADDR);
        if (StrUtils.isNotBlank(remoteAddr)) {
            return remoteAddr;
        }
        return request != null ? request.getRemoteAddr() : null;
    }

    public static String headerValue(
            @Nullable HttpServletRequest request,
            String headerName,
            String globalKey,
            boolean setGlobal
    ) {
        String value = "";
        if (
                ObjectUtils.isNotEmpty(request)
                        && StrUtils.isNotBlank(headerName)
        ) {
            value = request.getHeader(headerName);
        }
        if (
                StrUtils.isBlank(value)
                        && StrUtils.isNotBlank(globalKey)
        ) {
            value = GlobalData.get(globalKey);
        }
        if (
                setGlobal
                        && StrUtils.isNotBlank(globalKey)
                        && StrUtils.isNotBlank(value)
        ) {
            GlobalData.set(
                    globalKey,
                    value
            );
        }
        return value;
    }
}
