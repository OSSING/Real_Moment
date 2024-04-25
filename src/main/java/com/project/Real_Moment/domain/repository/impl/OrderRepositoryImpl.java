package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Order;
import com.project.Real_Moment.domain.entity.QOrder;
import com.project.Real_Moment.domain.enumuration.PaymentStatus;
import com.project.Real_Moment.domain.repository.custom.OrderRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.OrderDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.Real_Moment.domain.entity.QOrder.order;
import static com.project.Real_Moment.domain.entity.QOrderDetail.orderDetail;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public void updatePaymentComplete(Long orderId) {
        queryFactory
                .update(order)
                .set(order.status, PaymentStatus.PAYMENT_DONE)
                .set(order.orderedDate, LocalDateTime.now())
                .where(order.id.eq(orderId));
    }

    @Override
    public void updatePaymentCancel(Long orderId) {
        queryFactory
                .update(order)
                .set(order.status, PaymentStatus.CANCEL)
                .where(order.id.eq(orderId))
                .execute();
    }

    @Override
    public void updatePaymentRefundRequest(Long orderId) {
        queryFactory
                .update(order)
                .set(order.status, PaymentStatus.REFUND_REQUEST)
                .where(order.id.eq(orderId))
                .execute();
    }

    @Override
    public void updateReasonText(Long orderId, String reasonText) {
        queryFactory
                .update(order)
                .set(order.reasonText, reasonText)
                .where(order.id.eq(orderId))
                .execute();
    }

    @Override
    public void updatePaymentDone(Long orderId) {
        queryFactory
                .update(order)
                .set(order.status, PaymentStatus.DONE)
                .where(order.id.eq(orderId))
                .execute();
    }

    @Override
    public void updateOrderStatus(OrderDto.UpdateOrderStatus dto) {
        queryFactory
                .update(order)
                .set(order.status, PaymentStatus.getStatus(dto.getStatus()))
                .where(order.id.eq(dto.getOrderId()))
                .execute();
    }

    @Override
    public Page<Order> findByOrderListPage_Member(Long memberId, CondDto.MemberOrderListCond requestDto, Pageable pageable) {
        QOrder orderAlias = new QOrder("order2");
        List<Order> orderList = queryFactory
                .selectFrom(order)
                .from(orderDetail)
                .innerJoin(orderDetail.orderId, orderAlias)
                .where(order.memberId.id.eq(memberId),
                        itemNameEq(requestDto.getItemName()),
                        dateEq(requestDto.getStartDate(), requestDto.getLastDate()),
                        statusEq(requestDto.getStatus()))
                .orderBy(order.orderedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(order.count())
                .from(orderDetail)
                .innerJoin(orderDetail.orderId, order)
                .where(order.memberId.id.eq(memberId),
                        itemNameEq(requestDto.getItemName()),
                        dateEq(requestDto.getStartDate(), requestDto.getLastDate()),
                        statusEq(requestDto.getStatus()))
                .fetchOne();

        return new PageImpl<>(orderList, pageable, total);
    }

    @Override
    public Page<Order> findByOrderListPage_Admin(CondDto.AdminOrderListCond requestDto, Pageable pageable) {
        QOrder orderAlias = new QOrder("order2");
        List<Order> orderList = queryFactory
                .selectFrom(order)
                .from(orderDetail)
                .innerJoin(orderDetail.orderId, orderAlias)
                .where(itemNameEq(requestDto.getItemName()),
                        dateEq(requestDto.getStartDate(), requestDto.getLastDate()),
                        statusEq(requestDto.getStatus()),
                        loginIdEq(requestDto.getLoginId()),
                        merchantUidEq(requestDto.getMerchantUid()))
                .orderBy(order.orderedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(order.count())
                .from(orderDetail)
                .innerJoin(orderDetail.orderId, order)
                .where(itemNameEq(requestDto.getItemName()),
                        dateEq(requestDto.getStartDate(), requestDto.getLastDate()),
                        statusEq(requestDto.getStatus()),
                        loginIdEq(requestDto.getLoginId()),
                        merchantUidEq(requestDto.getMerchantUid()))
                .fetchOne();

        return new PageImpl<>(orderList, pageable, total);
    }

    // 상품 이름 기준으로 주문 내역 검색
    private BooleanExpression itemNameEq(String itemName) {
        return itemName != null ? orderDetail.itemId.name.contains(itemName) : null;
    }

    // 주문된 날짜 기준으로 주문 내역 검색
    private BooleanExpression dateEq(LocalDate startDate, LocalDate endDate) {

        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay().plusDays(1).minusSeconds(1);

            return order.orderedDate.between(startDateTime, endDateTime);
        }
        return null;
    }

    private BooleanExpression statusEq(String status) {
        return status != null ? order.status.eq(PaymentStatus.getStatus(status)) : null;
    }

    private BooleanExpression loginIdEq(String loginId) {
        return loginId != null ? order.memberId.loginId.eq(loginId) : null;
    }

    private BooleanExpression merchantUidEq(String merchantUid) {
        return merchantUid != null ? order.merchantUid.eq(merchantUid) : null;
    }
}
