package com.modoojeonse.config;

import com.modoojeonse.member.security.JwtAuthenticationFilter;
import com.modoojeonse.member.security.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic(AbstractHttpConfigurer::disable)
                // 쿠키 기반이 아닌 JWT 기반이므로 사용하지 않음
                .csrf(AbstractHttpConfigurer::disable)
                // CORS 설정
                .cors(c -> {
                            CorsConfigurationSource source = request -> {
                                // Cors 허용 패턴
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(
                                        List.of(
                                                "http://192.168.0.3:8083",
                                                "http://localhost:8083",
                                                "https://modoojeonse.movingjin.com"
                                        )
                                );
                                config.setMaxAge(3600L);
                                config.setAllowCredentials(true);
                                config.setAllowedMethods(
                                        List.of("PUT", "GET", "POST", "OPTIONS", "DELETE", "PATCH")
                                );
                                config.setExposedHeaders(List.of(
                                        "Authorization",
                                        "X-CSRF-TOKEN"
                                ));
                                config.setAllowedHeaders(List.of(
                                        "Access-Control-Allow-Headers",
                                        "Access-Control-Allow-Origin",
                                        "Access-Control-Expose-Headers",
                                        "Access-control-allow-credentials",
                                        "Accept",
                                        "X-Requested-With",
                                        "Authorization",
                                        "Cache-Control",
                                        "Content-Type",
                                        "Expires",
                                        "Last-Modified",
                                        "Content-Language",
                                        "Pragma",
                                        "Baeldung-Allowed",
                                        "Credential",
                                        "X-AUTH-TOKEN",
                                        "X-CSRF-TOKEN",
                                        JwtProvider.REFRESH_TOKEN
                                ));
                                return config;
                            };
                            c.configurationSource(source);
                        }
                )
                // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 조건별로 요청 허용/제한 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/register", "/login", "/emails/send-authcode", "/emails/verifications", "/find-email", "/find-pwd", "/modify-pwd", "/modify-info", "/news/**", "/geo/distance", "/review/search-native").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**", "/geo/**", "/review/**").hasRole("USER")
                        .anyRequest().denyAll()
                )
                // JWT 인증 필터 적용
                .addFilterBefore(new JwtAuthenticationFilter(redisTemplate, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                // 에러 핸들링
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                                response.setStatus(403);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("권한이 없는 사용자입니다.");
                            }
                        })
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                                // 인증문제가 발생했을 때 이 부분을 호출한다.
                                response.setStatus(401);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("Unauthenticated user.");
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}