package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.entity.*;
import com.project.Real_Moment.domain.repository.*;
import com.project.Real_Moment.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final WishRepository wishRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final ReviewRepository reviewRepository;
    private final ItemQARepository itemQARepository;
    private final QACommentRepository qaCommentRepository;
    private final OneOnOneRepository oneonOneRepository;
    private final CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkIdDuplicate(String loginId) {
        log.info("service.id = {}", loginId);
        return memberRepository.existsByLoginId(loginId);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public MemberDto.RegisterResponse memberSave(MemberDto.RegisterRequest dto) {

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
                .loginId(dto.getLoginId())
                .email(dto.getEmail())
                .loginPassword(passwordEncoder.encode(dto.getLoginPassword()))
                .name(dto.getName())
                .tel(dto.getTel())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .roles("ROLE_MEMBER")
                .build();
    }

//    @Transactional(readOnly = true)
//    public List<MemberDto.OrdersListDto> findOrdersList(Long id) {
//
//        return ordersRepository.findByMemberId_Id(id).stream()
//                .map(MemberDto.OrdersListDto::new).toList();
//    }

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

    @Transactional(readOnly = true)
    public AddressDto.AddressListPage findAddress(Long id, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);

        Page<Address> addressList = addressRepository.findAddressByPaging(id, pageable);

        List<AddressDto.AddressListResponse> addressListDto = addressList.stream()
                .map(AddressDto.AddressListResponse::new).toList();

        return new AddressDto.AddressListPage(addressListDto, addressList.getTotalPages(), nowPage);
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

    @Transactional(readOnly = true)
    public WishDto.WishListResponseWrapper getWishList(Long id, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);


        Page<Wish> wishList = wishRepository.findWishByMemberIdPaging(pageable, id, nowPage);

        List<WishDto.WishListResponse> wishListDto = wishList.stream()
                .map(WishDto.WishListResponse::new)
                .toList();


        for (WishDto.WishListResponse wishListResponse : wishListDto) {
            Wish wish = wishRepository.findById(wishListResponse.getWishId()).orElse(null);

            ItemDto.ItemResponse itemResponse = null;

            if (wish != null) {
                itemResponse = new ItemDto.ItemResponse(wish.getItemId());
            }

            wishListResponse.setItem(itemResponse);
        }

        return new WishDto.WishListResponseWrapper(wishListDto, wishList.getTotalPages(), nowPage);
    }

    @Transactional
    public void saveWish(Long id, WishDto.saveWish dto) {

        // save 수행 전 예외처리
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        // 중복 데이터 체크
        if (wishRepository.existsByItemIdAndMemberId(item, member)) {
            throw new IllegalArgumentException("이미 찜 목록에 존재하는 상품입니다.");
        }

        Wish wish = dto.toEntity(member, item);
        wishRepository.save(wish);
    }

    @Transactional
    public void deleteWish(Long id) {
        Wish wish = wishRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        wishRepository.delete(wish);
    }

    @Transactional(readOnly = true)
    public List<CartDto.CartListResponse> getCartList(Long id) {
        log.info("memberService.getCartList 실행!!!");
        List<Cart> cartList = cartRepository.findByMemberId_Id(id);

        return cartList.stream()
                .map(CartDto.CartListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveCart(Long id, CartDto.SaveCartRequest dto) {

        // 예외 처리
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(IllegalArgumentException::new);

        // 중복 체크
        if (cartRepository.existsByItemIdAndMemberId(item, member)) {
            throw new IllegalArgumentException("이미 장바구니 목록에 존재하는 상품입니다.");
        }

        // Dto -> Entity
        Cart cart = dto.toEntity(member, item, dto.getStock());

        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);
        cartRepository.delete(cart);
    }

    @Transactional
    public void changeCartCount(Long cartId, int stock) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);
        cartRepository.updateByStock(cartId, stock);
    }

    @Transactional(readOnly = true)
    public ReviewDto.MyReviewListResponse getMyReviewList(Long id, int nowPage) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return reviewRepository.findMyReviewListByMemberId(member, nowPage);
    }

    @Transactional
    public void saveReview(Long id, ReviewDto.SaveReviewRequest dto) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(IllegalArgumentException::new);

        if (reviewRepository.existsByMemberIdAndItemId(member, item)) {
            throw new IllegalArgumentException("이미 해당 상품에 대해 작성한 리뷰가 있습니다.");
        }

        reviewRepository.save(dto.toEntity(member, item));
    }

    @Transactional(readOnly = true)
    public ReviewDto.editReviewClick editReviewClick(Long id, Long reviewId) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (!reviewRepository.existsByIdAndMemberId(reviewId, member)) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        return new ReviewDto.editReviewClick(review);
    }

    @Transactional
    public void editReview(Long id, ReviewDto.editReviewClick dto) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (!reviewRepository.existsByIdAndMemberId(dto.getReviewId(), member)) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        reviewRepository.updateReview(dto);
    }

    @Transactional
    public void deleteReview(Long id, Long reviewId) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (!reviewRepository.existsByIdAndMemberId(reviewId, member)) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        reviewRepository.delete(review);
    }

    @Transactional(readOnly = true)
    public ItemQADto.MyItemQAListPage getMyItemQAList(Long memberId, int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);

        Page<ItemQA> itemQAList = itemQARepository.findMyItemQAListPage(memberId, pageable);

        List<ItemQADto.MyItemQAList> itemQAListDto = itemQAList.stream()
                .map(ItemQADto.MyItemQAList::new)
                .toList();

        for (ItemQADto.MyItemQAList myItemQAList : itemQAListDto) {
            QAComment qaComment = qaCommentRepository.findById(myItemQAList.getItemQAId()).orElse(null);
            Item item = itemRepository.findById(myItemQAList.getItem().getItemId()).orElse(null);

            ItemDto.ItemResponse itemDto = null;
            QACommentDto.QAComment qaCommentDto = null;

            if (item != null) {
                itemDto = new ItemDto.ItemResponse(item);
            }

            myItemQAList.setItem(itemDto);

            if (qaComment != null) {
                qaCommentDto = new QACommentDto.QAComment(qaComment);
            }

            myItemQAList.setQaComment(qaCommentDto);
        }

        return new ItemQADto.MyItemQAListPage(itemQAListDto, itemQAList.getTotalPages(), nowPage);
    }

    @Transactional
    public void saveQA(Long id, ItemQADto.SaveQARequest dto) {
        /**
         * dto에 담긴 itemId가 회원이 주문한 item이 맞는지 검증 로직 필요
         */
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        ItemQA itemQA = dto.toEntity(member, item);

        itemQARepository.save(itemQA);
    }

    @Transactional(readOnly = true)
    public ItemQADto.editQAClick editQAClick(Long memberId, Long itemQAId) {

        ItemQA itemQA = checkItemQAValidity(itemQAId, memberId);

        return new ItemQADto.editQAClick(itemQA);
    }

    @Transactional
    public void editQA(Long memberId, ItemQADto.editQAClick dto) {

        checkItemQAValidity(dto.getItemQAId(), memberId);

        itemQARepository.updateItemQA(memberId, dto);
    }

    @Transactional
    public void deleteQA(Long memberId, Long itemQAId) {
        ItemQA itemQA = checkItemQAValidity(itemQAId, memberId);

        itemQARepository.delete(itemQA);
    }

    private ItemQA checkItemQAValidity(Long itemQAId, Long memberId) {

        // 존재하는 ItemQA에 대한 요청인지 검증
        ItemQA itemQA = itemQARepository.findById(itemQAId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ItemQA 입니다."));

        // 요청된 ItemQA가 회원의 ItemQA인지 검증
        if (!itemQA.getMemberId().getId().equals(memberId)) {
            throw new IllegalArgumentException("회원의 itemQA가 아닙니다.");
        }

        return itemQA;
    }

    @Transactional(readOnly = true)
    public OneOnOneDto.OneOnOneWrapper getOneOnOneList(Long id, CondDto.OneOnOneListCond dto) {
        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 10);

        Page<OneOnOne> oneOnOneListByPaging = oneonOneRepository.findOneOnOneListByPaging(id, dto, pageable);

        List<OneOnOneDto.OneOnOneList> oneOnOneListDto = oneOnOneListByPaging.stream()
                .map(OneOnOneDto.OneOnOneList::new)
                .toList();

        for (OneOnOneDto.OneOnOneList oneOnOneList : oneOnOneListDto) {
            Comment comment = commentRepository.findById(oneOnOneList.getOneOnOneId()).orElse(null);

            CommentDto.commentResponse commentDto = null;

            if (comment != null) {
                commentDto = new CommentDto.commentResponse(comment);
            }

            oneOnOneList.setComment(commentDto);
        }

        return new OneOnOneDto.OneOnOneWrapper(oneOnOneListDto, oneOnOneListByPaging.getTotalPages(), dto.getNowPage());
    }

    @Transactional
    public void saveOneOnOne(Long id, OneOnOneDto.SaveOneOnOne dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 아닙니다."));

        oneonOneRepository.save(dto.toEntity(member));
    }
}