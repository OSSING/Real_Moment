package com.project.Real_Moment.auth.jwt;

import com.project.Real_Moment.auth.jwt.service.AuthService;
import com.project.Real_Moment.auth.jwt.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    @Autowired
    private TokenCache tokenCache;

//    private final RedisTemplate<String, String> redisTemplate;
    private static final String AUTHORITIES_KEY = "AUTH";

    private final String blacklistKey = AuthService.blacklistKey;

    private final String secretKey;

    private final long tokenValidityInMilliseconds;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private Key key;

    public TokenProvider(
            // application.yml에서 지정한 secret(64btye로 암호화)과 토큰 유지 시간을 가져옴
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secretKey = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;

        log.info("secret created: {}", this.secretKey);
    }

    // 64byte로 암호화된 secret을 복호화하고 그 값을 HMAC SHA 알고리즘을 적용한 키로 변환
    // 해당 키는 jwt 토큰을 서명하거나 검증할 때 사용
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("keyBytes: {}", keyBytes);
        log.info("Key created: {}", this.key);
    }

    // 토큰을 생성하는 메서드
    public String createAccessToken(Authentication authentication) {

        // 권한 종류 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // application.yml에서 지정한 만료 시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        // Access Token 생성 후 리턴
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {

        // 만료 시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds * 336);

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .signWith(this.key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();

//        // 생성된 Refresh Token을 Redis에 저장
//        redisTemplate.opsForValue().set(
//                authentication.getName(),
//                refreshToken,
//                tokenValidityInMilliseconds,
//                TimeUnit.MILLISECONDS
//        );

        // 생성된 RefreshToken을 저장
        tokenCache.setToken(authentication.getName(), refreshToken);

        // 생성된 RefreshToken 반환
        return refreshToken;

    }

    // Token에 담긴 정보로 Authentication 객체를 리턴하는 메서드
    public Authentication getAuthentication(String token) {

        // key로 토큰을 파싱하여 JWS로 변환 후 JWS의 본문(Claims) 객체 가져옴
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("jwt.getBody: {}", claims);

        if (claims.getSubject().equals(AUTHORITIES_KEY)) {
            // claims에 담긴 권한 정보를 추출 (","로 구분되었기에 split으로 각 권한을 나눠 List에 저장)
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            // 토큰의 주체(사용자 이름)을 가져와 User객체 생성
            User principal = new User(claims.getSubject(), "", authorities);

            // 유저 객체와 토큰, 권한 정보를 이용해서 최종적으로 Authentication 객체를 리턴 (SecurityContext에 저장되어 사용자 인증 정보로 활용)
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } else {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
    }

    /**
     * 에러를 전역으로 처리하는 Handler 구현 요망
     */
    // Token을 받아 파싱하여 나온 Exception을 catch하고 문제가 있다면 false, 정상이면 true 반환
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다람쥐");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토근입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
