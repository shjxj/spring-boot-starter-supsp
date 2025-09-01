package com.supsp.springboot.core.auth.impl;

import com.supsp.springboot.core.auth.IPassportService;
import com.supsp.springboot.core.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BasePassportService implements IPassportService {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Resource
    protected JwtUtil jwtUtil;

}
