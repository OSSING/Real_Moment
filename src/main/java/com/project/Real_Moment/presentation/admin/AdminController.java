package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminService;
import com.project.Real_Moment.auth.jwt.JwtFilter;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.auth.jwt.dto.TokenDto;
import com.project.Real_Moment.presentation.dto.AdminDto;
import com.project.Real_Moment.presentation.dto.CondDto;
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
public class AdminController {

    private final AdminService adminService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @PostMapping("/adminIdCheck")
    public ResponseEntity<Boolean> checkIdDuplicate(@RequestBody AdminDto.CheckIdDuplicate dto) {
        return ResponseEntity.ok().body(adminService.checkIdDuplicate(dto));
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<TokenDto> login(@RequestBody MemberDto.MemberLoginDto dto) {

        log.info("LoginDto = {}", dto.toString());

        // 요청받은 id와 password를 가지고 인증 전 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getLoginPassword());

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

    @PostMapping("/admin/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/adminList")
    public ResponseEntity<AdminDto.AdminListWrapper> getAdminList(@RequestParam(value = "loginId", required = false) String loginId,
                                                                  @RequestParam(value = "name", required = false) String name,
                                                                  @RequestParam(value = "roles", required = false) String roles,
                                                                  @RequestParam(value = "nowPage", required = false) Integer nowPage) {
        CondDto.AdminListCond dto = new CondDto.AdminListCond(loginId, name, roles, nowPage);
        return ResponseEntity.ok().body(adminService.getAdminList(dto));
    }

    @GetMapping("/admin/admin")
    public ResponseEntity<AdminDto.AdminList> getAdminDet(@RequestParam("adminId") Long adminId) {
        return ResponseEntity.ok().body(adminService.getAdminDet(adminId));
    }
}
