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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String loginId = authentication.getName();

        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

            if (userDetails.getAuthorities().stream().anyMatch(authority -> new SimpleGrantedAuthority("ROLE_MEMBER").equals(authority))) {
                Long memberId = customUserDetailsService.getMemberByLoginId(userDetails.getUsername());
                return Optional.of(memberId);
            } else {
                Long adminId = customUserDetailsService.getAdminByLoginId(userDetails.getUsername());
                return Optional.of(adminId);
            }

        } catch (UsernameNotFoundException e) {
            log.error("현재 Auditor를 위한 UserDetails 로드를 실패했습니다. : {}", loginId, e);
            return Optional.empty();
        }
    }
}
