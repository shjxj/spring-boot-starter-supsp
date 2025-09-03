package com.supsp.springboot.core.config.filter;


import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.DataKeys;
import com.supsp.springboot.core.helper.SystemCommon;
import com.supsp.springboot.core.threads.GlobalData;
import com.supsp.springboot.core.utils.IpUtils;
import com.supsp.springboot.core.utils.RequestUtils;
import com.supsp.springboot.core.vo.IpRegionInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SystemLocalDataFilter implements Filter {

    @Resource
    private CoreProperties coreProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        GlobalData.set(DataKeys.REQUEST_REMOTE_ADDR, httpServletRequest.getRemoteAddr());

        String ipAddress = IpUtils.getIpAddress(httpServletRequest);
        IpRegionInfo ipInfo = IpUtils.ipInfo(ipAddress);
        GlobalData.set(DataKeys.REQUEST_IP, ipAddress);
        GlobalData.set(DataKeys.REQUEST_IP_INFO, ipInfo);

        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderAppName(),
                DataKeys.REQUEST_APP_NAME,
                true
        );

        // 应用类型编码 字典 sysVersionType
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderAppCodeName(),
                DataKeys.REQUEST_APP_CODE,
                true
        );

        // 渠道
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderChannelName(),
                DataKeys.REQUEST_PLATFORM_NAME,
                true
        );

        // 应用平台 pc android ios ...
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderPlatformName(),
                DataKeys.REQUEST_PLATFORM_NAME,
                true
        );

        // 设备
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderDeviceName(),
                DataKeys.REQUEST_DEVICE_SN,
                true
        );

        // 设备 SN
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderDeviceSNName(),
                DataKeys.REQUEST_DEVICE_SN,
                true
        );

        // 应用版本
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderVersionName(),
                DataKeys.REQUEST_VERSION,
                true
        );

        // 强制更新
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderForceName(),
                DataKeys.REQUEST_FORCE_SUBMIT,
                true
        );

        // 操作人
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderOperatorIdName(),
                DataKeys.REQUEST_OPERATOR_ID,
                true
        );

        // 收银员
        RequestUtils.headerValue(
                httpServletRequest,
                CoreProperties.appHeaderCashierIdName(),
                DataKeys.REQUEST_CASHIER_ID,
                true
        );

        //
        SystemCommon.globalTokenDataSet(httpServletRequest, httpServletResponse);

        chain.doFilter(request, response);
    }
}
