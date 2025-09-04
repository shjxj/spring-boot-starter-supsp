package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.base.Result;
import com.supsp.springboot.core.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SysAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Result<String> result = new Result<>();
        result.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        result.setMessage("未认证，请登录");

        response.getWriter().write(
                JsonUtil.toJSONString(result)
        );
    }

}
