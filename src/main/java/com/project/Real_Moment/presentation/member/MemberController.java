package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.domain.repository.MemberRepository;
import com.project.Real_Moment.presentation.dto.*;
import com.project.Real_Moment.auth.jwt.TokenProvider;
import com.project.Real_Moment.application.member.MemberService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/{id}/logout")
    public ResponseEntity<Void> logout(@RequestHeader("RefreshToken") String refreshToken) {
        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경 후 자동 로그아웃
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
    public ResponseEntity<AddressDto.AddressListPage> findAddress(@PathVariable("id") Long id,
                                                                  @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
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
    public ResponseEntity<WishDto.WishListResponseWrapper> getWishList(@PathVariable("id") Long id,
                                                                       @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
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
    public ResponseEntity<ReviewDto.MyReviewListResponse> getMyReviewList(@PathVariable("id") Long id,
                                                                          @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
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
    public ResponseEntity<ItemQADto.MyItemQAListPage> getMyItemQAList(@PathVariable("id") Long id,
                                                                      @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
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
                                            @RequestParam(value = "answer", required = false) Boolean answer,
                                            @RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
        CondDto.OneOnOneListCond dto = new CondDto.OneOnOneListCond(answer, nowPage);
        return ResponseEntity.ok().body(memberService.getOneOnOneList(id, dto));
    }

    @PostMapping("{id}/oneOnOne")
    public ResponseEntity<Void> saveOneOnOne(@PathVariable("id") Long id, @RequestBody OneOnOneDto.SaveOneOnOne dto) {
        memberService.saveOneOnOne(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/oneOnOne")
    public ResponseEntity<OneOnOneDto.editOneOnOneClick> editOneOnOneClick(@PathVariable("id") Long id, @RequestParam("oneOnOneId") Long oneOnOneId) {
        return ResponseEntity.ok().body(memberService.editOneOnOneClick(id, oneOnOneId));
    }

    @PatchMapping("/{id}/oneOnOne")
    public ResponseEntity<Void> editOneOnOne(@PathVariable("id") Long id, @RequestBody OneOnOneDto.editOneOnOneClick dto) {
        memberService.editOneOnOne(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/oneOnOne")
    public ResponseEntity<Void> deleteOneOnOne(@PathVariable("id") Long id, @RequestParam("oneOnOneId") Long oneOnOneId) {
        memberService.deleteOneOnOne(id, oneOnOneId);
        return ResponseEntity.ok().build();
    }

    // 주문 전 구매 페이지에서 구매 견적 요청
    // get에서 List를 받기 위해서는 로직이 복잡해지기 때문에 Post로 요청
    @PostMapping("/{id}/order/page")
    public ResponseEntity<OrderDto.OrderItemList> getOrderQuote(@PathVariable("id") Long id, @RequestBody OrderDto.OrderItemListRequest dto) {
        return ResponseEntity.ok().body(memberService.getOrderQuote(id, dto));
    }

    // 주문 버튼 클릭 (임시 order, orderDetail 생성 및 결제창에 필요한 데이터 응답)
    @PostMapping("/{id}/payment/first")
    public ResponseEntity<OrderDto.PaymentResponse> getPaymentFirst(@PathVariable("id") Long id, @RequestBody OrderDto.PaymentFirst dto) {
        return ResponseEntity.ok().body(memberService.getPaymentFirst(id, dto));
    }

    // 결제창으로 결제 완료 후 반환받은 impUid를 요청
    @PostMapping("/{id}/payment/second")
    public ResponseEntity<Void> getPaymentSecond(@RequestBody OrderDto.PaymentSecond dto) {
        memberService.getPaymentSecond(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/orderList")
    public ResponseEntity<OrderDto.OrderListPaging> getOrderList(@PathVariable("id") Long id,
                                         @RequestParam(name = "itemName", required = false) String itemName,
                                         @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                         @RequestParam(name = "lastDate", required = false) LocalDate lastDate,
                                         @RequestParam(name = "status", required = false) String status,
                                         @RequestParam(name = "nowPage", required = false, defaultValue = "1") int nowPage) {
        CondDto.MemberOrderListCond dto = new CondDto.MemberOrderListCond(itemName, startDate, lastDate, status, nowPage);
        return ResponseEntity.ok().body(memberService.getOrderList(id, dto));
    }

    @GetMapping("/{id}/order")
    public ResponseEntity<OrderDto.OrderList> getOrder(@PathVariable("id") Long id,
                                     @RequestParam("orderId") Long orderId) {
        return ResponseEntity.ok().body(memberService.getOrder(id, orderId));
    }

    @PostMapping("/{id}/order/cancel")
    public ResponseEntity<Void> orderCancel(@PathVariable("id") Long id,
                                            @RequestBody OrderDto.OrderCancelRequest dto) throws IamportResponseException, IOException {
        memberService.orderCancel(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/order/refund")
    public ResponseEntity<Void> orderRefundRequest(@PathVariable("id") Long id,
                                                   @RequestBody OrderDto.OrderCancelRequest dto) {
        memberService.orderRefundRequest(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/order/done")
    public ResponseEntity<Void> orderDone(@PathVariable("id") Long id,
                                          @RequestBody OrderDto.OrderId dto) {
        memberService.orderDone(id, dto);
        return ResponseEntity.ok().build();
    }
}