package com.project.Real_Moment.auth.jwt.service;

import com.project.Real_Moment.auth.jwt.JwtFilter;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // refresh Token 받아 유효(만료시간 체크 등..)한지 확인하고 유효하다면 Access Token 재발급
    // refresh Token 유효하지 않다면, 클라이언트에게 새로운 로그인 요청
    // 클라이언트는 갱신된 Access Token 받아 이전 요청을 다시 수행
    public void reissueToken(HttpServletResponse response, String refreshToken) {

        // Refresh Token 에서 User 객체 받아 옴
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);

        String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName());

        // Redis 저장된 refresh, 요청받은 refresh 동일한지 체크
        if (!redisRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException();
        }

        // RefreshToken 과 갱신한 AccessToken Header 담아 반환
        response.setHeader(JwtFilter.ACCESSTOKEN_HEADER, tokenProvider.createAccessToken(authentication));
        response.setHeader(JwtFilter.REFRESHTOKEN_HEADER, refreshToken);

        log.info("====== AccessToken 재발급이 성공적으로 완료되었습니다!! ======");
    }
}
