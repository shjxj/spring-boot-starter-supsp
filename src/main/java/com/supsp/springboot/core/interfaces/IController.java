package com.supsp.springboot.core.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

public interface IController {

    HttpServletRequest getHttpServletRequest();

    void setHttpServletRequest(HttpServletRequest httpServletRequest);

    HttpServletResponse getHttpServletResponse();

    void setHttpServletResponse(HttpServletResponse httpServletResponse);

    ApplicationContext getApplicationContext();

    void setApplicationContext(ApplicationContext applicationContext);

    String getSid();
}
