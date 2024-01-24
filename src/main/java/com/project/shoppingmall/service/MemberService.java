package com.project.shoppingmall.service;

import com.project.shoppingmall.config.exception.DuplicateMemberException;
import com.project.shoppingmall.dto.RegisterDto;
import com.project.shoppingmall.entity.Member;
import com.project.shoppingmall.repository.AdminRepository;
import com.project.shoppingmall.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkIdDuplicate(String id) {
        log.info("service.id = {}", id);
        return memberRepository.existsById(id);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public RegisterDto.ResponseDto save(RegisterDto.RegisterRequest dto) {

        if (memberRepository.existsById(dto.getId())) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // 1. 요청받은 dto -> Entity로 변환
        Member member = createMember(dto);

        log.info("member.toString() = {}", member.toString());

        // 2. DB에 회원 저장

        // 3. 저장한 회원 데이터를 Dto로 변환 후 반환
        Member savedMember = memberRepository.save(member);

        return RegisterDto.ResponseDto.toDto(savedMember);
    }

    private Member createMember(RegisterDto.RegisterRequest dto) {

        return Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .tel(dto.getTel())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .memberRole("ROLE_MEMBER")
                .activated(true)
                .build();
    }
}
