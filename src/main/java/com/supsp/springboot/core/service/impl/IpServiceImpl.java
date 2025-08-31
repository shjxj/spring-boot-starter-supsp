package com.supsp.springboot.core.service.impl;

import com.supsp.springboot.core.service.IIpService;
import com.supsp.springboot.core.utils.Ip2regionSearcher;
import com.supsp.springboot.core.utils.IpUtils;
import com.supsp.springboot.core.utils.StrUtils;
import com.supsp.springboot.core.vo.IpRegionInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IpServiceImpl implements IIpService {

    @Resource
    private Ip2regionSearcher ip2regionSearcher;

    @Resource
    private HttpServletRequest httpServletRequest;

    // META-INF/dubbo

    @Override
    public IpRegionInfo search(String ip) {
        if (StrUtils.isBlank(ip)) {
            return null;
        }
        return ip2regionSearcher.search(ip);
    }

    @Override
    public IpRegionInfo search(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        try {
            return search(IpUtils.getIpAddress(request));
        } catch (Exception e) {
            //
        }
        return null;
    }

    @Override
    public IpRegionInfo search() {
        return search(httpServletRequest);
    }
}
