package com.project.Real_Moment.auth.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    // JwtFilter에서 AuthService를 통해 AccessToken 재발급 과정을 자동으로 수행해서 response에 담는다.
    @PostMapping("/member/reissue/accessToken")
    public ResponseEntity<Void> memberRefreshReissue() {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/reissue/accessToken")
    public ResponseEntity<Void> adminRefreshReissue() {

        return ResponseEntity.ok().build();
    }
}