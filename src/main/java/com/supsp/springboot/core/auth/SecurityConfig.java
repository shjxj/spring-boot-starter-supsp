package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.consts.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        // 启用 @PreAuthorize @PostAuthorize @PreFilter @PostFilter
        prePostEnabled = true,
        // 启用 @Secured
        securedEnabled = true,
        // 是否启用 @DenyAll @RolesAllowed @PermitAll
        jsr250Enabled = false
)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(Map<String, IPassportService> passportServiceMap) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(passportServiceMap);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new SysAuthenticationEntryPoint())
                        .accessDeniedHandler(new SysAccessDeniedHandler())
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // swagger
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/swagger-resources/configuration/security",
                                "/swagger-resources/configuration/ui",
                                "/v2/api-docs/**",
                                "/v3/api-docs/**",
                                // doc
                                "/doc.html",
                                "/webjars",
                                "/webjars/**",
                                "/webjars/**/**",
                                //
                                "/public/**",
                                "/common/**",
                                "/demo/**",
                                "/home/**",
                                "/open/**",
                                "/file/**",
                                // passport
                                "/admin/passport/**",
                                "/tenant/passport/**",
                                "/merchant/passport/**",
                                "/consumer/passport/**",
                                "/api/passport/**",
                                // public
                                "/admin/public/**",
                                "/tenant/public/**",
                                "/merchant/public/**",
                                "/consumer/public/**",
                                "/api/public/**",
                                // common
                                "/admin/common/**",
                                "/tenant/common/**",
                                "/merchant/common/**",
                                "/consumer/common/**",
                                "/api/common/**",
                                // demo
                                "/admin/demo/**",
                                "/tenant/demo/**",
                                "/merchant/demo/**",
                                "/consumer/demo/**",
                                "/api/demo/**",
                                // file
                                "/admin/file/**",
                                "/tenant/file/**",
                                "/merchant/file/**",
                                "/consumer/file/**",
                                "/api/file/**"
                        ).permitAll()
                        // HttpMethod.OPTIONS 放行
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/admin/**").hasRole(Constants.PERMISSION_ROLE_ADMIN)
                        .requestMatchers("/tenant/**").hasRole(Constants.PERMISSION_ROLE_TENANT)
                        .requestMatchers("/merchant/**").hasRole(Constants.PERMISSION_ROLE_MERCHANT)
                        .requestMatchers("/consumer/**").hasRole(Constants.PERMISSION_ROLE_CONSUMER)
                        .requestMatchers("/api/**").hasRole(Constants.PERMISSION_ROLE_API)
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
