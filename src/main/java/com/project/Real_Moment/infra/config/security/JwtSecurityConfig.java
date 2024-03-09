package com.project.Real_Moment.infra.config.security;

import com.project.Real_Moment.auth.jwt.JwtFilter;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.auth.jwt.service.AuthService;
import com.project.Real_Moment.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final MemberRepository memberRepository;

    // SecurityConfigurerAdapter에서 configure를 상속받아 Spring Security 설정을 추가하거나 변경 가능
    @Override
    public void configure(HttpSecurity http) throws Exception {

        // JwtFilter를 UsernamePasswordAuthenticationFilter 이전에 실행되도록 추가하여 사용자의 인증 여부 확인
        http.addFilterBefore(
                new JwtFilter(tokenProvider, authService, memberRepository),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
