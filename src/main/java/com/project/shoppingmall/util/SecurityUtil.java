package com.project.shoppingmall.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {

    // SecurityContext에서 Authentication 객체를 꺼내와 Username을 return 해주는 Util 메서드
    public static Optional<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 저장된 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        // "UserDetails"의 구현체인 "UsernamePasswordAuthenticationToken"에서 사용자 정보를 얻음
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        // 직접 String형으로 Principal 저장되어 있을 경우
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
