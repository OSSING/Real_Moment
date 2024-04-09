package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.application.member.MemberService;
import com.project.Real_Moment.auth.jwt.JwtFilter;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.auth.jwt.dto.TokenDto;
import com.project.Real_Moment.domain.repository.MemberRepository;
import com.project.Real_Moment.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입 도중 id 중복체크 (중복 o -> true, 중복 x -> false)
    @GetMapping("/memberIdCheck")
    public ResponseEntity<Boolean> checkIdDuplicate(@RequestBody String loginId) {
        return ResponseEntity.ok(memberService.checkIdDuplicate(loginId));
    }

    // 회원가입 요청
    @PostMapping("/join")
    public ResponseEntity<MemberDto.RegisterResponse> join(@RequestBody MemberDto.RegisterRequest dto) {
        log.info("RegisterDto.toString() = {}", dto.toString());

        // Service에 요청받은 회원 정보를 전송 후 반환받은 dto를 클라이언트에게 전송
        return ResponseEntity.ok(memberService.memberSave(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberDto.MemberLoginDto dto) {

        log.info("LoginDto = {}", dto.toString());

        // 요청받은 id와 password를 가지고 Spring Security에서 인증을 위해 사용되는 객체 생성
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
}
