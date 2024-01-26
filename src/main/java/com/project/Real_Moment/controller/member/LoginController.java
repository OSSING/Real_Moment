package com.project.Real_Moment.controller.member;

import com.project.Real_Moment.dto.LoginDto;
import com.project.Real_Moment.dto.RegisterDto;
import com.project.Real_Moment.dto.TokenDto;
import com.project.Real_Moment.jwt.JwtFilter;
import com.project.Real_Moment.jwt.TokenProvider;
import com.project.Real_Moment.repository.AdminRepository;
import com.project.Real_Moment.repository.MemberRepository;
import com.project.Real_Moment.service.MemberService;
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
@RequestMapping("/member")
public class LoginController {

    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입 도중 id 중복체크 (중복 o -> true, 중복 x -> false)
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable("id") String id) {
        log.info("controller.id = {}", id);
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkIdDuplicate(id));
        return ResponseEntity.ok(memberService.checkIdDuplicate(id));
    }

    // 회원가입 요청
    @PostMapping("/join")
    public ResponseEntity<RegisterDto.ResponseDto> join(@RequestBody RegisterDto.RegisterRequest dto) {
        log.info("RegisterDto.toString() = {}", dto.toString());

        // Service에 요청받은 회원 정보를 전송 후 반환받은 dto를 클라이언트에게 전송
        return ResponseEntity.ok(memberService.save(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto dto) {

        log.info("LoginDto = {}", dto.toString());

        // 요청받은 id와 password를 가지고 인증 전 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
