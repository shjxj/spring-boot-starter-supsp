package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.helper.AuthCommon;
import com.supsp.springboot.core.utils.JwtUtil;
import com.supsp.springboot.core.vo.auth.AuthAccount;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final Map<String, IPassportService> passportServiceMap;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   Map<String, IPassportService> passportServiceMap) {
        this.jwtUtil = jwtUtil;
        this.passportServiceMap = passportServiceMap;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        AuthMemberType memberType = AuthCommon.headerAuthMemberType(request);
        if (memberType == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String serviceName = memberType.getCode().toLowerCase() + "PassportService";
        if (!passportServiceMap.containsKey(serviceName)) {
            filterChain.doFilter(request, response);
            return;
        }
        IPassportService passportService = passportServiceMap.get(serviceName);
        AuthAccount authAccount = null;
        try {
            authAccount = passportService.auth(request);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            authAccount,
                            null,
                            List.of(() -> "ROLE_USER"));

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            //
        }

        filterChain.doFilter(request, response);
    }

}
