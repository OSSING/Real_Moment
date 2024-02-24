package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Orders;
import com.project.Real_Moment.domain.member.repository.OrdersRepository;
import com.project.Real_Moment.presentation.dto.MemberDto;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkIdDuplicate(String id) {
        log.info("service.id = {}", id);
        return memberRepository.existsById(id);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public MemberDto.RegisterDto memberSave(MemberDto.RegisterRequest dto) {

        if (memberRepository.existsById(dto.getId())) {

        }

        // 1. 요청받은 dto -> Entity로 변환
        Member member = createMember(dto);

        log.info("member.toString() = {}", member.toString());

        // 2. DB에 회원 저장

        // 3. 저장한 회원 데이터를 Dto로 변환 후 반환
        Member savedMember = memberRepository.save(member);

        return MemberDto.RegisterDto.toDto(savedMember);
    }

    private Member createMember(MemberDto.RegisterRequest dto) {

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

    @Transactional(readOnly = true)
    public List<MemberDto.OrdersListDto> findOrdersList(Long id) {

        List<Orders> ordersList = ordersRepository.findByMemberId_MemberId(id);

        return ordersList.stream()
                .map(MemberDto.OrdersListDto::new).toList();
    }

    @Transactional
    public MemberDto.PasswordChangeResponse changePassword(Long id, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = memberRepository.updatePasswordById(id, encodedPassword);

        return new MemberDto.PasswordChangeResponse(member);
    }

    @Transactional
    public MemberDto.EmailChangeResponse changeEmail(Long id, String email) {
        Member member = memberRepository.updateEmailById(id, email);

        return new MemberDto.EmailChangeResponse(member);
    }

    @Transactional
    public MemberDto.NameChangeResponse changeName(Long id, String name) {
        Member member = memberRepository.updateNameById(id, name);

        return new MemberDto.NameChangeResponse(member);
    }

    @Transactional
    public MemberDto.BirthDateChangeResponse changeBirthDate(Long id, LocalDate birthDate) {
        Member member = memberRepository.updateBirthDateById(id, birthDate);

        return new MemberDto.BirthDateChangeResponse(member);
    }
}