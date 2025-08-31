package com.supsp.springboot.core.service;


import com.supsp.springboot.core.vo.IpRegionInfo;
import jakarta.servlet.http.HttpServletRequest;

public interface IIpService {
    IpRegionInfo search(String ip);

    IpRegionInfo search(HttpServletRequest request);
    IpRegionInfo search();
}
