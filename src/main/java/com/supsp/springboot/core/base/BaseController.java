package com.supsp.springboot.core.base;

import com.supsp.springboot.core.interfaces.IController;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.context.ApplicationContext;

@Data
public abstract class BaseController implements IController {
    @Resource
    protected HttpServletRequest httpServletRequest;

    @Resource
    protected HttpServletResponse httpServletResponse;

    @Resource
    protected ApplicationContext applicationContext;

    @Override
    public String getSid() {
        return httpServletRequest.getSession().getId();
    }
}
