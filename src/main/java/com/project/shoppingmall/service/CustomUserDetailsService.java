package com.project.shoppingmall.service;

import com.project.shoppingmall.entity.Admin;
import com.project.shoppingmall.entity.Member;
import com.project.shoppingmall.repository.AdminRepository;
import com.project.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        return adminRepository.findOneWithAuthoritiesById(id)
                .map(admin -> createAdmin(id, admin))
                .orElseGet(() -> memberRepository.findOneWithAuthoritiesById(id)
                        .map(member -> createMember(id, member))
                        .orElseThrow(() -> new UsernameNotFoundException(id + " -> 데이터베이스에서 찾을 수 없습니다.")));
    }

    // DB에서 가져온 정보를 기준으로 Admin이 activated 상태라면 Admin의 권한 정보와 이름, 비밀번호를 담아 userdetails.User 객체를 return
    private org.springframework.security.core.userdetails.User createAdmin(String adminName, Admin admin) {
        if (!admin.isActivated()) {
            throw new RuntimeException(adminName + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = admin.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                admin.getId(),
                admin.getPassword(),
                grantedAuthorities);
    }

    // DB에 가져온 정보를 기준으로 Member가 activated 상태라면 Member의 권한 정보와 이름, 비밀번호를 담아 userdetails.User 객체를 return
    private org.springframework.security.core.userdetails.User createMember(String MemberName, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(MemberName + " -> 활성화 되어 있지 않습니다.");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(member.getMemberRole());

        return new org.springframework.security.core.userdetails.User(
                member.getId(),
                member.getPassword(),
                Collections.singletonList(authority));
    }
}
