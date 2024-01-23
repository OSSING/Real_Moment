package com.project.shoppingmall.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean { // Custom Filter

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    // 실제 필터링 로직을 작성하는 곳이며, 토큰의 인증 정보를 SecurityContext에 저장하는 역할을 수행
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // resloveToken 메서드를 통해 Request에서 토큰 정보를 가져옴
        String jwt = resolveToken(httpServletRequest);

        String requestURI = httpServletRequest.getRequestURI();

        // 가져온 토큰을 tokenProvider의 validateToken을 통해 토큰의 유효성을 검증
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

            // 토큰이 유효하다면 getAuthentication 메서드를 통해 authentication 객체를 받아옴
            Authentication authentication = tokenProvider.getAuthentication(jwt);

            // 받아온 유효 토큰의 authentication 객체를 SecurityContext에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Conext에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            log.debug("유효한 jwt 토큰이 없습니다, uri: {}", requestURI);
        }

        // 다음 필터를 실행하거나, 서블릿으로 전달
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
