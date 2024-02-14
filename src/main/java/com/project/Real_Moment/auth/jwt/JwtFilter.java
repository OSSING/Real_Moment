package com.project.Real_Moment.auth.jwt;

import com.project.Real_Moment.auth.jwt.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // Custom Filter

    private final TokenProvider tokenProvider;
    private final AuthService authService;

    public static final String ACCESSTOKEN_HEADER = "AccessToken";
    public static final String REFRESHTOKEN_HEADER = "RefreshToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("===== JwtFilter_doFilterInternal 실행 =====");

        // 헤더에서 refreshToken과 accessToken을 꺼냄
        String accessToken = resolveToken(request.getHeader(ACCESSTOKEN_HEADER));
        String refreshToken = resolveToken(request.getHeader(REFRESHTOKEN_HEADER));

//        // resloveToken 메서드를 통해 Request에서 AccessToken 정보를 가져옴
//        String jwt = resolveToken(request);

        String requestURI = request.getRequestURI();

        // access token만 들어온 경우
        if (!StringUtils.hasText(refreshToken) && StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            log.info("====== Access Token 요청 받음!! ======");
            log.info("요청받은 Access : {}", request.getHeader(ACCESSTOKEN_HEADER));

            // 토큰이 유효하다면 getAuthentication 메서드를 통해 authentication 객체를 받아옴
            Authentication authentication = tokenProvider.getAuthentication(accessToken);

            // 받아온 유효 토큰의 authentication 객체를 SecurityContext에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Conext에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        // access token을 갱신하기 위해 refresh token만 들어온 경우
        } else if (!StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken) && tokenProvider.validateToken(refreshToken)) {
            log.info("====== Refresh Token 요청 받음!! ======");
            log.info("요청받은 Refresh : {}", request.getHeader(REFRESHTOKEN_HEADER));

            authService.reissueToken(response, refreshToken);
        }

        // 다음 필터를 실행하거나, 서블릿으로 전달
        filterChain.doFilter(request, response);
    }

    private String resolveToken(String bearerToken) {

        log.info("bearerToken: {}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
