package com.supsp.springboot.core.auth;

import com.supsp.springboot.core.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final Map<String, IPassportService> passportServiceMap;



    public SecurityConfig(JwtUtil jwtUtil,
                          Map<String, IPassportService> passportServiceMap) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, passportServiceMap);
        this.passportServiceMap = passportServiceMap;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
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
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
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
