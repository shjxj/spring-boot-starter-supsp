package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.auth.annotations.RequiresPermissions;
import com.supsp.springboot.core.auth.annotations.RequiresRoles;
import com.supsp.springboot.core.enums.Logical;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Aspect
public class AuthAspect {

    @Around("@annotation(com.supsp.springboot.core.auth.annotations.RequiresPermissions) || @annotation(com.supsp.springboot.core.auth.annotations.RequiresRoles)")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("未认证用户无法访问");
        }

        Set<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // 权限校验
        RequiresPermissions rp = method.getAnnotation(RequiresPermissions.class);
        if (rp != null) {
            boolean passed = check(rp.value(), rp.logical(), authorities);
            if (!passed) throw new AccessDeniedException("权限不足");
        }

        // 角色校验（ROLE_前缀处理）
        RequiresRoles rr = method.getAnnotation(RequiresRoles.class);
        if (rr != null) {
            Set<String> roles = authorities.stream()
                    .filter(a -> a.startsWith("ROLE_"))
                    .map(a -> a.substring(5))
                    .collect(Collectors.toSet());

            boolean passed = check(rr.value(), rr.logical(), roles);
            if (!passed) throw new AccessDeniedException("角色不足");
        }

        return joinPoint.proceed();
    }

    private boolean check(String[] required, Logical logical, Set<String> actual) {
        return logical == Logical.OR
                ? Arrays.stream(required).anyMatch(actual::contains)
                : Arrays.stream(required).allMatch(actual::contains);
    }

}
