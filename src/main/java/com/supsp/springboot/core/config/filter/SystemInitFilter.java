package com.supsp.springboot.core.config.filter;


import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.DataKeys;
import com.supsp.springboot.core.helper.SystemCommon;
import com.supsp.springboot.core.threads.GlobalData;
import com.supsp.springboot.core.utils.RequestUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class SystemInitFilter implements Filter {

    @Resource
    private CoreProperties coreProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        log.debug(
//                """
//                        SystemInitFilter
//                        tokenAdminDepHeaderName: {}
//                        servletPath: {}
//                        servletPathArray: {}
//                        pathModule: {}
//                        pathApp: {}
//                        HeaderNames: {}
//                        requestHeaders: {}
//                        """,
//                CoreProperties.tokenAdminDepHeaderName(),
//                RequestUtils.servletPath(httpServletRequest),
//                JsonUtil.toJSONString(RequestUtils.servletPathArray(httpServletRequest)),
//                RequestUtils.pathModule(httpServletRequest),
//                RequestUtils.pathApp(httpServletRequest),
//                JsonUtil.toJSONString(RequestUtils.requestHeaderNames(httpServletRequest)),
//                JsonUtil.toJSONString(RequestUtils.getHeaders(httpServletRequest))
//        );

        SystemCommon.initTrace(httpServletRequest, httpServletResponse);

        SystemCommon.globalBaseDataSet(httpServletRequest, httpServletResponse);

        List<String> headerNames = RequestUtils.requestHeaderNames(httpServletRequest);
        GlobalData.set(DataKeys.REQUEST_HEADER_NAMES, headerNames);

        Map<String, String> headers = RequestUtils.requestHeaders(httpServletRequest);
        GlobalData.set(DataKeys.REQUEST_HEADERS, headers);

        SystemCommon.globalTokenDataSet(httpServletRequest, httpServletResponse);

        chain.doFilter(request, response);
    }
}
