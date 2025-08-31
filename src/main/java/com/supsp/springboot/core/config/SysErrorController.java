package com.supsp.springboot.core.config;

import com.supsp.springboot.core.base.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SysErrorController implements ErrorController {
    @RequestMapping("/error")
    public Result<String> handleError(HttpServletRequest request, HttpServletResponse response) {
        int code = response.getStatus();
        response.setStatus(code);
        // log.debug("SysErrorController handleError: {};", code);
        if (HttpStatus.NOT_FOUND.value() == code) {
            return Result.fail(code, "未找到资源");
        } else if (HttpStatus.FORBIDDEN.value() == code) {
            return Result.fail(code, "没有访问权限");
        } else if (HttpStatus.BAD_REQUEST.value() == code) {
            return Result.fail(code, "请求参数错误");
        } else if (HttpStatus.UNAUTHORIZED.value() == code) {
            return Result.fail(code, "登录过期");
        } else if (HttpStatus.METHOD_NOT_ALLOWED.value() == code) {
            return Result.fail(code, "没有权限");
        } else {
            return Result.fail(code, "请稍后再试");
        }
    }

    @RequestMapping("/login")
    public Result<String> handleLogin(HttpServletRequest request, HttpServletResponse response) {
//        log.debug("SysErrorController handleLogin");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return Result.fail(HttpStatus.UNAUTHORIZED.value(), "请登录");
    }

    @RequestMapping("/unauthorized")
    public Result<String> handleUnauthorized(HttpServletRequest request, HttpServletResponse response) {
//        log.debug("SysErrorController handleUnauthorized");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return Result.fail(HttpStatus.UNAUTHORIZED.value(), "请登录");
    }

}
