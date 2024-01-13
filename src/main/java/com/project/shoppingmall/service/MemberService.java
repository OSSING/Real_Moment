package com.project.shoppingmall.service;

import com.project.shoppingmall.dto.RegisterDto;
import com.project.shoppingmall.entity.Member;
import com.project.shoppingmall.repository.AdminsRepository;
import com.project.shoppingmall.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AdminsRepository adminsRepository;

    @Transactional
    public boolean checkIdDuplicate(String id) {
        log.info("service.id = {}", id);
        return memberRepository.existsById(id);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public String save(RegisterDto.Request dto) {
        // 1. 요청받은 dto -> Entity로 변환
        Member member = dto.toEntity();

        // 2. 아이디 중복 확인
        boolean byNickName = memberRepository.existsById(member.getId());

        // 3. 아아디가 중복이라면 프론트에서 검증
        if (byNickName) {
            Member savedMember = memberRepository.save(member);
            return null;
        }

        // 4. 아이디가 중복이 아니라면 save 후 Entity -> DTO 변환 후 반환
        return null;
    }
}
