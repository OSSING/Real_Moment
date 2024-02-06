package com.project.Real_Moment.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // Custom Filter

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("===== JwtFilter_doFilterInternal 실행 =====");
        log.info("요청받은 jwt : {}", request.getHeader(AUTHORIZATION_HEADER));

        // resloveToken 메서드를 통해 Request에서 토큰 정보를 가져옴
        String jwt = resolveToken(request);

        String requestURI = request.getRequestURI();

        // 가져온 토큰을 tokenProvider의 validateToken을 통해 토큰의 유효성을 검증
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

            // 토큰이 유효하다면 getAuthentication 메서드를 통해 authentication 객체를 받아옴
            Authentication authentication = tokenProvider.getAuthentication(jwt);

            // 받아온 유효 토큰의 authentication 객체를 SecurityContext에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Conext에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            log.info("유효한 jwt 토큰이 없습니다, uri: {}", requestURI);
        }

        // 다음 필터를 실행하거나, 서블릿으로 전달
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        log.info("bearerToken: {}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
