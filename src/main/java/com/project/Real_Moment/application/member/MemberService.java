package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.Wish;
import com.project.Real_Moment.domain.member.repository.*;
import com.project.Real_Moment.presentation.dto.AddressDto;
import com.project.Real_Moment.presentation.dto.MemberDto;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.presentation.dto.WishDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final WishRepository wishRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;

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
    public List<AddressDto.AddressListResponse> findAddress(Long id) {
        return addressRepository.findAddressByMemberId_MemberId(id).stream()
                .map(AddressDto.AddressListResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void saveAddress(Long id, AddressDto.SaveAddressRequest dto) {
        Member member = memberRepository.findById(id).orElse(null);
        Address address = dto.toEntity(member);
        addressRepository.save(address);
    }

    @Transactional
    public void updateAddress(AddressDto.AddressRequest dto) {

        // update 쿼리 수행 전 예외처리
        addressRepository.findById(dto.getAddressId()).orElseThrow(IllegalArgumentException::new);

        addressRepository.updateAddress(dto);
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        addressRepository.delete(address);
    }

    @Transactional
    public List<WishDto.WishListResponse> getWishList(Long id) {
        return wishRepository.findWishByMemberId(id);
    }

    @Transactional
    public void saveWish(Long id, WishDto.saveWish dto) {

        // save 수행 전 예외처리
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        // 중복 데이터 체크
        if (wishRepository.existsByItemIdAndMemberId(item, member)) {
            log.info("이미 찜 목록에 존재하는 상품입니다.");
            throw new IllegalArgumentException();
        }

        Wish wish = dto.toEntity(member, item);
        wishRepository.save(wish);
    }

    public void deleteWish(Long id) {
        Wish wish = wishRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        wishRepository.delete(wish);
    }
}