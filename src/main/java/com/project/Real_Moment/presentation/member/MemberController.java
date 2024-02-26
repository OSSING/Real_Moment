package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.presentation.dto.AddressDto;
import com.project.Real_Moment.presentation.dto.MemberDto;
import com.project.Real_Moment.auth.jwt.dto.TokenDto;
import com.project.Real_Moment.auth.jwt.JwtFilter;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.application.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

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
    public ResponseEntity<MemberDto.RegisterResponse> join(@RequestBody MemberDto.RegisterRequest dto) {
        log.info("RegisterDto.toString() = {}", dto.toString());

        // Service에 요청받은 회원 정보를 전송 후 반환받은 dto를 클라이언트에게 전송
        return ResponseEntity.ok(memberService.memberSave(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberDto.MemberLoginDto dto) {

        log.info("LoginDto = {}", dto.toString());

        // 요청받은 id와 password를 가지고 인증 전 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);

        log.info("로그인 성공 후 생성된 Access: {}", accessToken);
        log.info("로그인 성공 후 생성된 Refresh: {}", refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.ACCESSTOKEN_HEADER, "Bearer " + accessToken);
        httpHeaders.add(JwtFilter.REFRESHTOKEN_HEADER, "Bearer " + refreshToken);

        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }

    // 마이 페이지 (주문 목록)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEMBER')")
    public ResponseEntity<List<MemberDto.OrdersListDto>> myPageMain(@PathVariable("id") Long id) {

        return ResponseEntity.ok(memberService.findOrdersList(id));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("RefreshToken") String refreshToken) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changePassword(@PathVariable("id") Long id, @RequestBody MemberDto.PasswordChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changePassword(id, request.getPassword()));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changeEmail(@PathVariable("id") Long id, @RequestBody MemberDto.EmailChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changeEmail(id, request.getEmail()));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changeName(@PathVariable("id") Long id, @RequestBody MemberDto.NameChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changeName(id, request.getName()));
    }

    @PatchMapping("/{id}/birth")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changeBirthDate(@PathVariable("id") Long id, @RequestBody MemberDto.BirthDateChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changeBirthDate(id, request.getBirthDate()));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> deleteMember(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMember(id));
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<AddressDto.AddressListResponse>> findAddresses(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.findAddresses(id));
    }
}
