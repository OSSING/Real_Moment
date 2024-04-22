# Real Moment
Restful API 서버 프로젝트 / 화장품 판매 쇼핑몰


## ⏰ 개발 기간
- 2024.01.01 ~ 


## 🤝 참여 인원
- FE 1명, 본인 포함 BE 2명


## 🔍 기술 스택
- Spring Boot
- Spring Security
- Spring Data JPA
- Querydsl
- PostgreSQL
- JWT
- AWS S3


## 🏃 프로젝트 소개
웹 개발에 대해 공부한 뒤로 처음 경험해보는 프로젝트였기에 프로젝트 주제에 대해 고민이 많았습니다.

팀원들과 고민 끝에 웹 개발과 관련한 다양한 기술 스택이 접목되어 많은 경험을 할 수 있는 화장품 쇼핑몰로 주제를 정하게 되었습니다.

ERD와 FlowChart를 설계하고 작성된 API 명세에 따라 서로 다른 독립적인 서버를 개발하기로 결정했습니다.


## 🤖 Architecture
![아키텍처 drawio](https://github.com/OSSING/Real_Moment/assets/98817068/1cf71af3-b925-430f-a73f-28b4fbba3aac)


## 📊 ERD
![쇼핑몰 erd](https://github.com/OSSING/Real_Moment/assets/98817068/71114233-90fd-46ae-a461-843093c247da)


## 프로젝트 핵심 기능
### JWT를 활용한 로그인 기능

AccessToken의 만료 기간은 30분이며, RefreshToken의 만료 기간은 1주일로 설정했습니다.

- LoginController
```
@PostMapping("/login")
public ResponseEntity<TokenDto> login(@RequestBody MemberDto.MemberLoginDto dto) {

    // 요청받은 id와 password를 가지고 Spring Security에서 인증을 위해 사용되는 authenticationToken 객체 생성
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getLoginPassword());

    // AuthenticationManager로 authenticationToken을 검증하고 유효한 경우 Authentication객체를 반환
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 현재 사용자의 인증 정보를 설정
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 토큰 생성
    String accessToken = tokenProvider.createAccessToken(authentication);
    String refreshToken = tokenProvider.createRefreshToken(authentication);

    TokenDto tokenDto = new TokenDto(accessToken, refreshToken);

    log.info("로그인 성공 후 생성된 Access: {}", accessToken);
    log.info("로그인 성공 후 생성된 Refresh: {}", refreshToken);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.ACCESSTOKEN_HEADER, "Bearer " + accessToken);
    httpHeaders.add(JwtFilter.REFRESHTOKEN_HEADER, "Bearer " + refreshToken);

    // 최근 로그인 시간 갱신
    memberService.memberLogin(dto.getLoginId());

    return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
}
```

먼저 application.yml에 작성된 인코딩된 Key를 Base64로 디코딩하여 HMAC 알고리즘에 적합한 Key Byte 배열을 얻습니다.

Key Byte 배열을 HMAC-SHA256 알고리즘을 통해 암호화하여 토큰을 생성합니다.

이로써 발급된 Token은 위조되지 않았음을 검증 할 수 있습니다.

- Token 생성에 사용될 Key Byte 배열 생성
```
@Override
public void afterPropertiesSet() throws Exception {
    byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
}
```
RefreshToken의 생성과 동시에 Redis에 저장하여 추후 AccessToken을 재발급할 때 사용합니다.

- Token 생성 코드의 일부
```
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

    // 생성된 Refresh Token을 Redis에 저장
    redisTemplate.opsForValue().set(
            authentication.getName(),
            refreshToken,
            tokenValidityInMilliseconds,
            TimeUnit.MILLISECONDS
    );

    // 생성된 RefreshToken 반환
    return refreshToken;
}
```

### 검색 조건를 활용한 검색 기능
### 회원 / 관리자 전용 페이지 분리
### 관리자의 등급에 따른 접근 가능한 API 분리
### AWS S3를 통한 이미지 관리
### PortOne를 통한 결제 및 환불 처리





[ReadMe 작성법 참고 블로그](https://backendcode.tistory.com/165)
