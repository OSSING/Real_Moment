package com.project.Real_Moment.infra.config.security;

import com.project.Real_Moment.auth.jwt.JwtAccessDeniedHandler;
import com.project.Real_Moment.auth.jwt.JwtAuthenticationEntryPoint;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.auth.jwt.service.AuthService;
import com.project.Real_Moment.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private final AuthService authService;
    private final MemberRepository memberRepository;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable
                .csrf(AbstractHttpConfigurer::disable)

                // Exception을 Handling할 때 jwtAccessDeniedHandler와 jwtAuthenticationEntryPoint를 추가
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                "/join",
                                "/memberIdCheck",
                                "/login",
                                "/member/{id}/logout",
                                "/admin/{id}/logout",
                                "/category",
                                "/itemList",
                                "/item",
                                "/reviewList",
                                "/QAList",
                                "/announcementList",
                                "/announcement",
                                "/adminLogin",
                                "/admin/reissue/accessToken",
                                "/member/reissue/accessToken",
                                "/upload").permitAll()

                        // 모든 관리자 가능 (조회)
                        .requestMatchers(
                                "/admin/main/view",
                                "/admin/orderList/view",
                                "/admin/order/view",
                                "/admin/announcementList/view",
                                "/admin/reviewList/view",
                                "/admin/QAList/view",
                                "/admin/oneOnOneList/view",
                                "/admin/itemList/view",
                                "/admin/item/view",
                                "/admin/category/view",
                                "/admin/memberList/view",
                                "/admin/member/view",
                                "/admin/{id}"
                        ).hasAnyRole("REPRESENTATIVE", "OPERATOR", "CUSTOMER", "ADMIN")

                        // 관리자 대표
                        .requestMatchers(
                                "/adminJoin",
                                "/adminIdCheck",
                                "/admin/adminList",
                                "/admin/admin/**",
                                "/admin/grade"
                                ).hasAnyRole("REPRESENTATIVE")

                        // 고객 관리자
                        .requestMatchers(
                                "/admin/{id}/announcement/**",
                                "/admin/{id}/QAComment/**",
                                "/admin/{id}/comment"
                                ).hasAnyRole("CUSTOMER", "REPRESENTATIVE")

                        // 운영 관리자
                        .requestMatchers(
                                "/admin/order/**",
                                "/admin/item/**",
                                "/admin/category"
                        ).hasAnyRole("OPERATOR", "REPRESENTATIVE")

                        .anyRequest().hasAnyRole("MEMBER")
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig도 적용
                .with(new JwtSecurityConfig(tokenProvider, authService, memberRepository), customizer -> {});

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/h2-console/**", "/favicon.ico");
    }
}
