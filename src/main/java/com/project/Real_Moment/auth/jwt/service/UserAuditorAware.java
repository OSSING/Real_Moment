package com.project.Real_Moment.auth.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuditorAware implements AuditorAware<Long> {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Optional<Long> getCurrentAuditor() {

        // authentication 객체
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication : {}", authentication);

        // 인증된 사용자가 없다면 Optional.empty() 반환
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        // 인증된 사용자의 로그인 Id
        String loginId = authentication.getName();

        try {

            // authentication의 Authorities가 ROLE_MEMBER라면 회원, 아니라면 관리자에서 식별자를 추출하여 반환
            if (authentication.getAuthorities().stream().anyMatch(authority -> new SimpleGrantedAuthority("ROLE_MEMBER").equals(authority))) {
                Long memberId = customUserDetailsService.getMemberByLoginId(loginId);
                return Optional.of(memberId);
            } else {
                Long adminId = customUserDetailsService.getAdminByLoginId(loginId);
                return Optional.of(adminId);
            }

        } catch (UsernameNotFoundException e) {
            log.error("현재 Auditor를 위한 UserDetails 로드를 실패했습니다. : {}", loginId, e);
            return Optional.empty();
        }
    }
}
