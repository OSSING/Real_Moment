package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.entity.*;
import com.project.Real_Moment.domain.enumuration.Gender;
import com.project.Real_Moment.domain.enumuration.PaymentStatus;
import com.project.Real_Moment.domain.repository.*;
import com.project.Real_Moment.presentation.dto.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final GradeRepository gradeRepository;
    private final S3FileRepository s3FileRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final IamportClient iamportClient;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean checkIdDuplicate(String loginId) {
        log.info("service.id = {}", loginId);
        return memberRepository.existsByLoginId(loginId);
    }

    // 회원가입시 요청받은 Dto를 Entity로 변환 후 저장
    @Transactional
    public MemberDto.RegisterResponse memberSave(MemberDto.RegisterRequest dto) {

        // 1. 회원에게 적용할 기본 등급 생성
        Grade grade = gradeRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 등급입니다."));

        if (!grade.getGradeName().equals("Moment")) {
            throw new IllegalArgumentException("잘못된 등급입니다.");
        }

        // 2. 요청받은 dto -> Entity로 변환
        Member member = dto.toEntity();
//        Member member = createMember(dto, grade);

        log.info("member.toString() = {}", member.toString());

        // 3. 저장한 회원 데이터를 Dto로 변환 후 반환
        Member savedMember = memberRepository.save(member);

        return MemberDto.RegisterResponse.toDto(savedMember);
    }

//    private Member createMember(MemberDto.RegisterRequest dto, Grade grade) {
//        return Member.builder()
//                .gradeId(grade)
//                .loginId(dto.getLoginId())
//                .email(dto.getEmail())
//                .loginPassword(passwordEncoder.encode(dto.getLoginPassword()))
//                .name(dto.getName())
//                .tel(dto.getTel())
//                .birthDate(dto.getBirthDate())
//                .gender(Gender.getConstant(dto.getGender()))
//                .roles("ROLE_MEMBER")
//                .build();
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
    public ReviewDto.MyReviewListResponse getMyReviewList(Long id, Integer nowPage) {
        int pageNumber = (nowPage != null && nowPage > 0) ? nowPage : 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 9);


        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Page<Review> reviewListPaging = reviewRepository.findMyReviewListByMemberId(pageable, member);

        List<ReviewDto.MyReview> reviewList = reviewListPaging.stream()
                .map(ReviewDto.MyReview::new)
                .toList();

        for (ReviewDto.MyReview reviewDto : reviewList) {
            Review review = reviewRepository.findById(reviewDto.getReviewId()).orElse(null);

            ItemDto.ItemResponse itemDto = null;

            if (review != null) {
                itemDto = new ItemDto.ItemResponse(review.getItemId());
                List<ItemDto.MainImgListResponse> mainImgUrl = s3FileRepository.findMainImg_UrlByItemId(review.getItemId());
                itemDto.setMainImg(mainImgUrl);
            }

            reviewDto.setItem(itemDto);
        }

        return new ReviewDto.MyReviewListResponse(reviewList, reviewListPaging.getTotalPages(), pageNumber);
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
            QACommentDto.QACommentResponse qaCommentDto = null;

            if (item != null) {
                itemDto = new ItemDto.ItemResponse(item);
            }

            myItemQAList.setItem(itemDto);

            if (qaComment != null) {
                qaCommentDto = new QACommentDto.QACommentResponse(qaComment);
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

            CommentDto.CommentResponse commentDto = null;

            if (comment != null) {
                commentDto = new CommentDto.CommentResponse(comment);
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

    @Transactional(readOnly = true)
    public OneOnOneDto.editOneOnOneClick editOneOnOneClick(Long id, Long oneOnOneId) {
        OneOnOne oneOnOne = checkOneOnOneValidity(oneOnOneId, id);
        return new OneOnOneDto.editOneOnOneClick(oneOnOne);
    }

    @Transactional
    public void editOneOnOne(Long memberId, OneOnOneDto.editOneOnOneClick dto) {
        checkOneOnOneValidity(dto.getOneOnOneId(), memberId);
        oneonOneRepository.updateOneOnOne(memberId, dto);
    }

    @Transactional
    public void deleteOneOnOne(Long memberId, Long oneOnOneId) {
        OneOnOne oneOnOne = checkOneOnOneValidity(oneOnOneId, memberId);
        oneonOneRepository.delete(oneOnOne);
    }

    private OneOnOne checkOneOnOneValidity(Long oneOnOneId, Long memberId) {

        // 존재하는 OneOnOne에 대한 요청인지 검증
        OneOnOne oneOnOne = oneonOneRepository.findById(oneOnOneId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 OneOnOneId 입니다."));

        // 요청된 OneOnOne가 회원의 OneOnOne인지 검증
        if (!oneOnOne.getMemberId().getId().equals(memberId)) {
            throw new IllegalArgumentException("회원의 OneOnOne가 아닙니다.");
        }

        return oneOnOne;
    }

    @Transactional
    public OrderDto.OrderItemList getOrderQuote(Long id, OrderDto.OrderItemListRequest requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        List<OrderDto.OrderItemRequest> requestList = requestDto.getRequestList();
        List<OrderDto.OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;
        int totalDiscountPrice = 0;
        int totalSellPrice = 0;

        // 요청 Dto itemId, count
        for (OrderDto.OrderItemRequest itemRequest : requestList) {
            Long itemId = itemRequest.getItemId();
            int count = itemRequest.getCount();

            // request에 담긴 itemId로 상품 조회
            Optional<Item> itemOptional = itemRepository.findById(itemId);
            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();

                // 상품의 메인 이미지 가져오기
                List<ItemDto.MainImgListResponse> mainImgUrl = s3FileRepository.findMainImg_UrlByItemId(item);

                // 주문 총 정가 계산
                totalPrice = item.getPrice() * count;

                // 주문 총 할인가격 계산
                totalDiscountPrice = item.getDiscountPrice() * count;

                // 주문 총 가격 계산
                totalSellPrice += item.getSellPrice() * count;

                // 상품 정보로 응답 Dto 값 채우기
                OrderDto.OrderItem orderItem = new OrderDto.OrderItem(item, count, mainImgUrl);

                orderItems.add(orderItem);
            }
        }

        /**
         * 적립금은 결제하는 회원의 등급으로 적립 수치가 결정되며
         * 할인을 적용하기 전 상품의 정가를 기준으로 적립된다.
         */

        // 회원이 현재 보유 중인 적립금
        int point = member.getPoint();

        // 회원이 주문을 통해 획득할 예상 적립금
        int getPoint = (int) (totalPrice * (member.getGradeId().getRewardRate() / 100.0));

        // 주문 Dto 생성
        OrderDto.OrderPrice orderPrice = new OrderDto.OrderPrice(totalPrice, totalDiscountPrice, totalSellPrice, point, getPoint);

        // 주문 상세 리스트와 주문 Dto 객체 반환
        return new OrderDto.OrderItemList(orderItems, orderPrice);
    }

    @Transactional
    public OrderDto.PaymentResponse getPaymentFirst(Long memberId, OrderDto.PaymentFirst requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 아닙니다."));

        String merchantUid = UUID.randomUUID().toString();

        // 임시로 Order 객체를 생성
        Order order = Order.builder()
                .memberId(member)
                .totalPrice(requestDto.getTotalPrice())
                .totalDiscountPrice(requestDto.getTotalDiscountPrice())
                .usePoint(requestDto.getUsePoint())
                .getPoint(requestDto.getGetPoint())
                .buyPrice(requestDto.getBuyPrice())
                .name(requestDto.getName())
                .mainAddress(requestDto.getMainAddress())
                .detAddress(requestDto.getDetAddress())
                .requestText(requestDto.getRequestText())
                .tel(requestDto.getTel())
                .status(PaymentStatus.PAYMENT_READY)
                .merchantUid(merchantUid)
                .build();

        orderRepository.save(order);

        List<OrderDto.OrderItemRequest> requestItems = requestDto.getItems();

        // 여러 종류를 한 번에 결제 할 때 PG사에 요청할 상품 이름 지정
        Item firstItem = itemRepository.findById(requestItems.get(0).getItemId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        int itemTypeCount = requestItems.size();
        String itemName;
        if (itemTypeCount > 1) {
            itemName = firstItem.getName() + "외 " + itemTypeCount + "개";
        } else {
            itemName = firstItem.getName();
        }

        for (OrderDto.OrderItemRequest requestItem : requestItems) {
            Long itemId = requestItem.getItemId();
            int count = requestItem.getCount();

            Optional<Item> itemOptional = itemRepository.findById(itemId);
            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();

                // 임시 OrderDetail 객체 생성
                OrderDetail orderDetail = OrderDetail.builder()
                        .itemId(item)
                        .orderId(order)
                        .price(item.getPrice())
                        .discountRate(item.getDiscountRate())
                        .discountPrice(item.getDiscountPrice())
                        .sellPrice(item.getSellPrice())
                        .itemCount(count)
                        .totalPrice(item.getSellPrice() * count)
                        .build();

                orderDetailRepository.save(orderDetail);
            }
        }

        // 기본 배송지
        Address address = addressRepository.findByMemberIdAndIsDefAddressIsTrue(member);
        String responseAddress = address.getMainAddress() + " " + address.getDetAddress();

        return new OrderDto.PaymentResponse(merchantUid, itemName, requestDto.getBuyPrice(), member.getName(), member.getEmail(), responseAddress);
    }

    @Transactional
    public void getPaymentSecond(OrderDto.PaymentSecond requestDto) {
        try {

            // 결제 단건 조회
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(requestDto.getImpUid());

            // 주문 내역 조회
            Order order = orderRepository.findByMerchantUid(requestDto.getMerchantUid())
                    .orElseThrow(() -> new IllegalArgumentException("존재하는 주문이 아닙니다."));

            // 결제 완료가 아니라면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {

                // 주문, 주문 상세 내역 삭제
                orderRepository.delete(order);

                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            int price = order.getBuyPrice();

            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if (iamportPrice != price) {
                // 주문, 주문 상세 내역 삭제
                orderRepository.delete(order);

                // 결제 금액 위변조로 의심되는 결제를 취소
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                throw new RuntimeException("결제금액 위변조 의심이 감지되었습니다.");
            }

            // 결제 상태 변경 (결제 완료)
            orderRepository.updatePaymentComplete(order.getId());
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public OrderDto.OrderListPaging getOrderList(Long memberId, CondDto.OrderListCond requestDto) {
        int pageNumber = (requestDto.getNowPage() != null && requestDto.getNowPage() > 0) ? requestDto.getNowPage() : 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 9);

        Page<Order> orderListPaging = orderRepository.findByOrderListPage(memberId, requestDto, pageable);

        List<OrderDto.OrderList> orderListDto = orderListPaging.stream()
                .map(OrderDto.OrderList::new)
                .toList();

        for (OrderDto.OrderList orderDto : orderListDto) {
            orderDto.setOrderDetails(getOrderDetailListDto(orderDto.getOrderId()));
        }

        return new OrderDto.OrderListPaging(orderListDto, orderListPaging.getTotalPages(), pageNumber);
    }

    @Transactional(readOnly = true)
    public OrderDto.OrderById getOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 주문이 아닙니다."));

        return new OrderDto.OrderById(order, getOrderDetailListDto(orderId));
    }

    private List<OrderDetailDto.OrderDetailList> getOrderDetailListDto(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 주문이 아닙니다."));

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order);

        return orderDetailList.stream()
                .map(orderDetail -> {
                    Item item = itemRepository.findById(orderDetail.getItemId().getId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

                    // 상품의 메인 이미지 가져오기
                    List<ItemDto.MainImgListResponse> mainImgList = s3FileRepository.findMainImg_UrlByItemId(item);

                    ItemDto.OrderedItemList orderedItemList = new ItemDto.OrderedItemList(item, mainImgList);

                    return new OrderDetailDto.OrderDetailList(orderDetail, orderedItemList);
                })
                .toList();
    }

    @Transactional
    public void orderCancel(Long memberId, OrderDto.OrderCancelRequest requestDto) throws IamportResponseException, IOException {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        // 결제 취소
        paymentCancel(order);

        // 결제 취소 사유
        orderRepository.updateReasonText(requestDto.getOrderId(), requestDto.getReasonText());
    }

    // 결제 취소를 요청하는 메서드
    private void paymentCancel(Order order) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(order.getImpUid());

        // 결제된 가격 조회
        int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

        // 결제 취소 (포트원)
        IamportResponse<Payment> cancelResponse =
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

        if (cancelResponse.getCode() == 0) {
            orderRepository.updatePaymentCancel(order.getId());
        } else {
            throw new RuntimeException("결제 취소에 실패하였습니다. : " + cancelResponse.getMessage());
        }
    }

    @Transactional
    public void orderRefundRequest(Long memberId, OrderDto.OrderCancelRequest requestDto) {

        // 환불 요청
        orderRepository.updatePaymentRefundRequest(requestDto.getOrderId());

        // 환불 요청 사유
        orderRepository.updateReasonText(requestDto.getOrderId(), requestDto.getReasonText());
    }


    @Transactional
    public void orderDone(Long memberId, OrderDto.OrderId requestDto) {

        // 구매 학정 요청
        orderRepository.updatePaymentDone(requestDto.getOrderId());
    }
}