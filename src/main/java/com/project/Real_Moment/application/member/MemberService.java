package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Addresses;
import com.project.Real_Moment.domain.member.repository.AddressesRepository;
import com.project.Real_Moment.domain.member.repository.OrdersRepository;
import com.project.Real_Moment.presentation.dto.AddressesDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final AddressesRepository addressesRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkIdDuplicate(String id) {
        log.info("service.id = {}", id);
        return memberRepository.existsById(id);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public MemberDto.RegisterResponse memberSave(MemberDto.RegisterRequest dto) {

//        if (memberRepository.existsById(dto.getId())) {
//
//        }

        // 1. 요청받은 dto -> Entity로 변환
        Member member = createMember(dto);

        log.info("member.toString() = {}", member.toString());

        // 2. DB에 회원 저장

        // 3. 저장한 회원 데이터를 Dto로 변환 후 반환
        Member savedMember = memberRepository.save(member);

        return MemberDto.RegisterResponse.toDto(savedMember);
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

        return ordersRepository.findByMemberId_MemberId(id).stream()
                .map(MemberDto.OrdersListDto::new).toList();
    }

    @Transactional
    public MemberDto.MemberInfoUpdateResponse changePassword(Long id, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = memberRepository.updatePasswordById(id, encodedPassword);

        return new MemberDto.MemberInfoUpdateResponse(member);
    }

    @Transactional
    public MemberDto.MemberInfoUpdateResponse changeEmail(Long id, String email) {
        Member member = memberRepository.updateEmailById(id, email);

        return new MemberDto.MemberInfoUpdateResponse(member);
    }

    @Transactional
    public MemberDto.MemberInfoUpdateResponse changeName(Long id, String name) {
        Member member = memberRepository.updateNameById(id, name);

        return new MemberDto.MemberInfoUpdateResponse(member);
    }

    @Transactional
    public MemberDto.MemberInfoUpdateResponse changeBirthDate(Long id, LocalDate birthDate) {
        Member member = memberRepository.updateBirthDateById(id, birthDate);

        return new MemberDto.MemberInfoUpdateResponse(member);
    }

    @Transactional
    public Boolean deleteMember(Long id) {
        if (memberRepository.updateActivatedById(id) > 0) {
            log.info("회원 삭제가 성공적으로 처리되었습니다.");
            return true;
        } else {
            log.info("회원 삭제에 실패했습니다.");
            return false;
        }
    }

    @Transactional
    public List<AddressesDto.AddressListResponse> findAddresses(Long id) {
        return addressesRepository.findAddressesByMemberId_MemberId(id).stream()
                .map(AddressesDto.AddressListResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void saveAddress(Long id, AddressesDto.SaveAddressRequest dto) {
        Member member = memberRepository.findById(id).orElse(null);
        Addresses address = dto.toEntity(member);
        addressesRepository.save(address);
    }

    @Transactional
    public AddressesDto.AddressResponse updateAddress(AddressesDto.AddressRequest dto) {
//        Member member = memberRepository.findById(id).orElse(null);
//        Addresses address = dto.toEntity(member);
        Addresses updatedAddress = addressesRepository.shivar(dto);

        return new AddressesDto.AddressResponse(updatedAddress);
    }
}