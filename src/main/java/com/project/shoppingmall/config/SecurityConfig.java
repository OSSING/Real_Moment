package com.project.shoppingmall.config;

import com.project.shoppingmall.jwt.JwtAccessDeniedHandler;
import com.project.shoppingmall.jwt.JwtAuthenticationEntryPoint;
import com.project.shoppingmall.jwt.JwtSecurityConfig;
import com.project.shoppingmall.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // Spring Security에서 사용자의 비밀번호를 암호화하거나,
    // 로그인 시에 저장된 암호화된 비밀번호와 입력된 비밀번호를 비교할 때 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable
                .csrf(AbstractHttpConfigurer::disable)

                // Exception을 Handling할 때 jwtAccessDeniedHandler와 jwtAuthenticationEntryPoint를 추가
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // 회원가입가 로그인 API는 토큰이 없는 상태에서 접근하기 때문에 .permitAll()로 허용
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                "/member/join",
                                "/member/login",
                                "/main",
                                "/category",
                                "/items",
                                "/item",
                                "/reviews",
                                "/QAList",
                                "/Announcements",
                                "/Announcement",
                                "/admin/login").permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 적용
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/h2-console/**", "/favicon.ico");
    }
}
