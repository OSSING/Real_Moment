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
    public Member save(RegisterDto.RegisterRequest dto) {
        log.info("RegisterRequest.toString() = {}", dto.toString());

        // 1. 요청받은 dto -> Entity로 변환
        Member member = dto.toEntity();
        log.info("member.toString() = {}", member.toString());

        // 2. DB에 회원 저장

        // 3. 저장한 회원 데이터를 Dto로 변환 후 반환
        return memberRepository.save(member);
    }
}
