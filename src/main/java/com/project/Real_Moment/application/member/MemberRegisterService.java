package com.project.Real_Moment.application.member;

import com.project.Real_Moment.presentation.dto.MeberDto;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkIdDuplicate(String id) {
        log.info("service.id = {}", id);
        return memberRepository.existsById(id);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public MeberDto.RegisterDto memberSave(MeberDto.RegisterRequest dto) {

        if (memberRepository.existsById(dto.getId())) {

        }

        // 1. 요청받은 dto -> Entity로 변환
        Member member = createMember(dto);

        log.info("member.toString() = {}", member.toString());

        // 2. DB에 회원 저장

        // 3. 저장한 회원 데이터를 Dto로 변환 후 반환
        Member savedMember = memberRepository.save(member);

        return MeberDto.RegisterDto.toDto(savedMember);
    }

    private Member createMember(MeberDto.RegisterRequest dto) {

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
