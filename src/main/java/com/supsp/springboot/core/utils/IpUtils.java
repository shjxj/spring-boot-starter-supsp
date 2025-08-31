package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.service.IIpService;
import com.supsp.springboot.core.vo.IpRegionInfo;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class IpUtils {

    public static final String UNKNOWN = "unknown";

    public static final String X_FORWARDED_FOR = "x-forwarded-for";

    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    public static final String X_REAL_IP = "X-Real-IP";

    public static final String LOCAL_IP_V4 = "127.0.0.1";

    public static final String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";

    public static final String CDN_SRC_IP = "cdn-src-ip";

    private final static List<String> IP_HEADERS = Arrays.asList(
            CDN_SRC_IP,
            X_FORWARDED_FOR,
            PROXY_CLIENT_IP,
            WL_PROXY_CLIENT_IP,
            X_REAL_IP
    );

    private static IIpService ipService;

    public IpUtils(IIpService ipService) {
        IpUtils.ipService = ipService;
    }

    /**
     * 获取请求IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(@Nullable HttpServletRequest request) {

        if (request == null) {
            return null;
        }
        String ip = RequestUtils.getHeader(IP_HEADERS, request);
        if (StrUtils.isBlank(ip)) {
            ip = RequestUtils.getRemoteAddr(request);
        }
        if (StrUtils.isBlank(ip)) {
            return UNKNOWN;
        }

        if (
                ip.equals(LOCAL_IP_V4)
                        || ip.equals(LOCAL_IP_V6)
        ) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                log.error("getIpAddress error", e);
            }
        }
        return ip;
    }


    public static IpRegionInfo ipInfo(String ip) {
        try {
            return ipService.search(ip);
        } catch (Exception e) {
            log.error("ipInfo error ", e);
        }
        return null;
    }

    public static IpRegionInfo ipInfo(HttpServletRequest request) {
        try {
            return ipService.search(getIpAddress(request));
        } catch (Exception e) {
            log.error("ipInfo error ", e);
        }
        return null;
    }
}
