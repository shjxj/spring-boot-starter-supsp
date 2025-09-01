package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.vo.auth.AuthAccount;
import com.supsp.springboot.core.vo.auth.LoginData;
import com.supsp.springboot.core.vo.auth.LoginParams;
import jakarta.servlet.http.HttpServletRequest;

public interface IPassportService {
    LoginData login(LoginParams params) throws ModelException;

    String authToken(HttpServletRequest servletRequest);

    AuthAccount auth(HttpServletRequest request) throws ModelException;
}
