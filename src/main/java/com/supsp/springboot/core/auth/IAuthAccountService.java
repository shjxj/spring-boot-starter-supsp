package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.exceptions.AuthException;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthAccountService {

    Object getPrincipal() throws AuthException;

    String getUid(HttpServletRequest request);

    String getUid();

    String getSid();

    String getSid(HttpServletRequest request);

    IAuthAccount getCurrentAuthAccount() throws AuthException;

    IAuthAccount getAuthAccount() throws AuthException;

    AuthMemberType getMemberType() throws AuthException;

    Long getId() throws AuthException;

    String getAccount() throws AuthException;

    AccountType getAccountType() throws AuthException;

    String getName() throws AuthException;

    Long getAvatar() throws AuthException;

    String getPwd() throws AuthException;
}
