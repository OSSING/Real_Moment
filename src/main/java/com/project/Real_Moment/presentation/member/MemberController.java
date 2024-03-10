package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.presentation.dto.*;
import com.project.Real_Moment.auth.jwt.dto.TokenDto;
import com.project.Real_Moment.auth.jwt.JwtFilter;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.application.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입 도중 id 중복체크 (중복 o -> true, 중복 x -> false)
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable("id") String id) {
        log.info("controller.id = {}", id);
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkIdDuplicate(id));
        return ResponseEntity.ok(memberService.checkIdDuplicate(id));
    }

    // 회원가입 요청
    @PostMapping("/join")
    public ResponseEntity<MemberDto.RegisterResponse> join(@RequestBody MemberDto.RegisterRequest dto) {
        log.info("RegisterDto.toString() = {}", dto.toString());

        // Service에 요청받은 회원 정보를 전송 후 반환받은 dto를 클라이언트에게 전송
        return ResponseEntity.ok(memberService.memberSave(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberDto.MemberLoginDto dto) {

        log.info("LoginDto = {}", dto.toString());

        // 요청받은 id와 password를 가지고 인증 전 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getLoginPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);

        log.info("로그인 성공 후 생성된 Access: {}", accessToken);
        log.info("로그인 성공 후 생성된 Refresh: {}", refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.ACCESSTOKEN_HEADER, "Bearer " + accessToken);
        httpHeaders.add(JwtFilter.REFRESHTOKEN_HEADER, "Bearer " + refreshToken);

        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }

    // 마이 페이지 (주문 목록)
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MEMBER')")
//    public ResponseEntity<List<MemberDto.OrdersListDto>> myPageMain(@PathVariable("id") Long id) {
//
//        return ResponseEntity.ok(memberService.findOrdersList(id));
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("RefreshToken") String refreshToken) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changePassword(@PathVariable("id") Long id, @RequestBody MemberDto.PasswordChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changePassword(id, request.getLoginPassword()));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changeEmail(@PathVariable("id") Long id, @RequestBody MemberDto.EmailChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changeEmail(id, request.getEmail()));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changeName(@PathVariable("id") Long id, @RequestBody MemberDto.NameChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changeName(id, request.getName()));
    }

    @PatchMapping("/{id}/birth")
    public ResponseEntity<MemberDto.MemberInfoUpdateResponse> changeBirthDate(@PathVariable("id") Long id, @RequestBody MemberDto.BirthDateChangeRequest request) {
        return ResponseEntity.ok().body(memberService.changeBirthDate(id, request.getBirthDate()));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> deleteMember(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMember(id));
    }

    @GetMapping("/{id}/addressList")
    public ResponseEntity<AddressDto.AddressListPage> findAddress(@PathVariable("id") Long id, @RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok(memberService.findAddress(id, nowPage));
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<Void> saveAddress(@PathVariable("id") Long id, @RequestBody AddressDto.SaveAddressRequest request) {
        log.info("AddressDto.AddAddressRequest.toString() = {}", request.toString());
        memberService.saveAddress(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<Void> updateAddress(@RequestBody AddressDto.AddressRequest request) {
        memberService.updateAddress(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/address")
    public ResponseEntity<Void> deleteAddress(@RequestParam("addressId") Long id) {
        memberService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/wishList")
    public ResponseEntity<WishDto.WishListResponseWrapper> getWishList(@PathVariable("id") Long id, @RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok().body(memberService.getWishList(id, nowPage));
    }

    @PostMapping("/{id}/wish")
    public ResponseEntity<Void> saveWish(@PathVariable("id") Long id, @RequestBody WishDto.saveWish dto) {
        memberService.saveWish(id, dto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/wish")
    public ResponseEntity<Void> deleteWish(@RequestParam("wishId") Long id) {
        memberService.deleteWish(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/cartList")
    public ResponseEntity<List<CartDto.CartListResponse>> getCartList(@PathVariable("id") Long id) {
        log.info("memberController.getCartList 실행!!!");
        return ResponseEntity.ok().body(memberService.getCartList(id));
    }

    @PostMapping("/{id}/cart")
    public ResponseEntity<Void> saveCart(@PathVariable("id") Long id, @RequestBody CartDto.SaveCartRequest dto) {
        memberService.saveCart(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/cart")
    public ResponseEntity<Void> deleteCart(@RequestParam("cartId") Long cartId) {
        memberService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cart/count")
    public ResponseEntity<Void> changeCartCount(@RequestParam("cartId") Long cartId, @RequestParam("stock") int stock) {
        memberService.changeCartCount(cartId, stock);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/reviewList")
    public ResponseEntity<ReviewDto.MyReviewListResponse> getMyReviewList(@PathVariable("id") Long id, @RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok().body(memberService.getMyReviewList(id, nowPage));
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Void> saveReview(@PathVariable("id") Long id, @RequestBody ReviewDto.SaveReviewRequest dto) {
        memberService.saveReview(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/review/data")
    public ResponseEntity<ReviewDto.editReviewClick> editReviewClick(@PathVariable("id") Long id, @RequestParam("reviewId") Long reviewId) {
        return ResponseEntity.ok().body(memberService.editReviewClick(id, reviewId));
    }

    @PatchMapping("/{id}/review")
    public ResponseEntity<Void> editReview(@PathVariable("id") Long id, @RequestBody ReviewDto.editReviewClick dto) {
        memberService.editReview(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/review")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id, @RequestParam("reviewId") Long reviewId) {
        memberService.deleteReview(id, reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/QAList")
    public ResponseEntity<ItemQADto.MyItemQAListPage> getMyItemQAList(@PathVariable("id") Long id, @RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok().body(memberService.getMyItemQAList(id, nowPage));
    }

    @PostMapping("/{id}/QA")
    public ResponseEntity<Void> saveQA(@PathVariable("id") Long id, @RequestBody ItemQADto.SaveQARequest dto) {
        memberService.saveQA(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/QA/data")
    public ResponseEntity<ItemQADto.editQAClick> editQAClick(@PathVariable("id") Long id, @RequestParam("itemQAId") Long itemQAId) {
        return ResponseEntity.ok().body(memberService.editQAClick(id, itemQAId));
    }

    @PatchMapping("/{id}/QA")
    public ResponseEntity<Void> editQA(@PathVariable("id") Long id, @RequestBody ItemQADto.editQAClick dto) {
        memberService.editQA(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/QA")
    public ResponseEntity<Void> deleteQA(@PathVariable("id") Long id, @RequestParam("itemQAId") Long itemQAId) {
        memberService.deleteQA(id, itemQAId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/oneOnOneList")
    public ResponseEntity<OneOnOneDto.OneOnOneWrapper> getOneOnOneList(@PathVariable("id") Long id,
                                            @RequestParam("answer") Boolean answer,
                                            @RequestParam("nowPage") int nowPage) {
        CondDto.OneOnOneListCond dto = new CondDto.OneOnOneListCond(answer, nowPage);
        return ResponseEntity.ok().body(memberService.getOneOnOneList(id, dto));
    }
}
