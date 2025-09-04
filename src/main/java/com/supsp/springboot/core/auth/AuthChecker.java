package com.supsp.springboot.core.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component("authChecker")
public class AuthChecker {

    // @PreAuthorize("@authChecker.hasPermissions(authentication, {'user:add','user:delete'}, 'AND')")
    // @PreAuthorize("@authChecker.hasPermissions(authentication, {'user:view','user:export'}, 'OR')")
    public boolean hasPermissions(Authentication authentication, String[] perms, String logical) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if ("OR".equalsIgnoreCase(logical)) {
            return Arrays.stream(perms).anyMatch(authorities::contains);
        } else {
            return Arrays.stream(perms).allMatch(authorities::contains);
        }
    }

    // @PreAuthorize("@authChecker.hasRoles(authentication, {'admin','manager'}, 'AND')")
    // @PreAuthorize("@authChecker.hasRoles(authentication, {'editor','reviewer'}, 'OR')")
    public boolean hasRoles(Authentication authentication, String[] roles, String logical) {
        Set<String> roleSet = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                // 去掉 ROLE_ 前缀
                .map(auth -> auth.substring(5))
                .collect(Collectors.toSet());

        if ("OR".equalsIgnoreCase(logical)) {
            return Arrays.stream(roles).anyMatch(roleSet::contains);
        } else {
            return Arrays.stream(roles).allMatch(roleSet::contains);
        }
    }
}

