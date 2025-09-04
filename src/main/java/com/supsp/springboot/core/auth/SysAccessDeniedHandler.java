package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.base.Result;
import com.supsp.springboot.core.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SysAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        Result<String> result = new Result<>();
        result.setCode(HttpServletResponse.SC_FORBIDDEN);
        result.setMessage("权限不足，拒绝访问");

        response.getWriter().write(
                JsonUtil.toJSONString(result)
        );
    }

}
