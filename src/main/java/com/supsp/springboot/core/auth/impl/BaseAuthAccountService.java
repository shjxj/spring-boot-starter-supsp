package com.supsp.springboot.core.auth.impl;

import com.supsp.springboot.core.auth.IAuthAccountService;
import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.exceptions.AuthException;
import com.supsp.springboot.core.helper.AuthCommon;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;

public abstract class BaseAuthAccountService implements IAuthAccountService {

    @Resource
    protected HttpServletRequest request;

    @Override
    public Object getPrincipal() throws AuthException {
        return AuthCommon.getPrincipal();
    }

    @Override
    public String getUid(HttpServletRequest request) {
        return "";
    }

    @Override
    public String getUid() {
        return "";
    }

    @Override
    public String getSid() {
        return "";
    }

    @Override
    public String getSid(HttpServletRequest request) {
        return "";
    }

    @Override
    public IAuthAccount getCurrentAuthAccount() throws AuthException {
        return null;
    }

    @Override
    public IAuthAccount getAuthAccount() throws AuthException {
        Object principal = this.getPrincipal();
        if (
                ObjectUtils.isNotEmpty(principal)
        ){
            try {
                return (IAuthAccount) principal;
            } catch (Exception e) {
                // log.error("exception message", e);
            }
        }
        return getCurrentAuthAccount();
    }

    @Override
    public AuthMemberType getMemberType() throws AuthException {
        return null;
    }

    @Override
    public Long getId() throws AuthException {
        return 0L;
    }

    @Override
    public String getAccount() throws AuthException {
        return "";
    }

    @Override
    public AccountType getAccountType() throws AuthException {
        return null;
    }

    @Override
    public String getName() throws AuthException {
        return "";
    }

    @Override
    public Long getAvatar() throws AuthException {
        return 0L;
    }

    @Override
    public String getPwd() throws AuthException {
        return "";
    }
}
