package com.project.Real_Moment.auth.jwt.service;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.AdminAuthority;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.AdminRepository;
import com.project.Real_Moment.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        return adminRepository.findOneWithAuthoritiesByLoginId(loginId)
                .map(admin -> createAdmin(loginId, admin))
                .orElseGet(() -> memberRepository.findOneWithAuthoritiesByLoginId(loginId)
                        .map(member -> createMember(loginId, member))
                        .orElseThrow(() -> new UsernameNotFoundException(loginId + " -> 데이터베이스에서 찾을 수 없습니다.")));
    }

//    private org.springframework.security.core.userdetails.User createAdmin(String adminName, Admin admin) {
//        if (!admin.isDelete()) {
//            throw new RuntimeException(adminName + " -> 활성화되어 있지 않습니다.");
//        }
//
//        List<GrantedAuthority> grantedAuthorities = admin.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());
//        log.info("admin's authority: {}", grantedAuthorities);
//
//        return new org.springframework.security.core.userdetails.User(
//                admin.getLoginId(),
//                admin.getLoginPassword(),
//                grantedAuthorities);
//    }

    // DB에서 가져온 정보를 기준으로 Admin이 activated 상태라면 Admin의 권한 정보와 이름, 비밀번호를 담아 userdetails.User 객체를 return
    private org.springframework.security.core.userdetails.User createAdmin(String AdminName, Admin admin) {
        if (admin.isDelete()) {
            log.info("활성화 되어 있지 않은 사용자입니다.");
            throw new RuntimeException(AdminName + " -> 활성화 되어 있지 않습니다.");
        }

        EnumSet<AdminAuthority> rolesSet = EnumSet.of(admin.getRoles());
        List<SimpleGrantedAuthority> authorities = rolesSet.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        log.info("admin's authority: {}", authorities);

        return new org.springframework.security.core.userdetails.User(
                admin.getLoginId(),
                admin.getLoginPassword(),
                authorities);
    }

    // DB에 가져온 정보를 기준으로 Member가 activated 상태라면 Member의 권한 정보와 이름, 비밀번호를 담아 userdetails.User 객체를 return
    private org.springframework.security.core.userdetails.User createMember(String MemberName, Member member) {
        if (member.isDelete()) {
            log.info("활성화 되어 있지 않은 사용자입니다.");
            throw new RuntimeException(MemberName + " -> 활성화 되어 있지 않습니다.");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(member.getRoles());
        log.info("member's authority: {}", authority);

        return new org.springframework.security.core.userdetails.User(
                member.getLoginId(),
                member.getLoginPassword(),
                Collections.singletonList(authority));
    }
}
