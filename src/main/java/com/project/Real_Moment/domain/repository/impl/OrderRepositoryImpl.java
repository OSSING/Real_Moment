package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Order;
import com.project.Real_Moment.domain.entity.QOrder;
import com.project.Real_Moment.domain.enumuration.PaymentStatus;
import com.project.Real_Moment.domain.repository.custom.OrderRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
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
    public Page<Order> findByOrderListPage(Long memberId, CondDto.OrderListCond requestDto, Pageable pageable) {
        QOrder orderAlias = new QOrder("order2");
        List<Order> orderList = queryFactory
                .selectFrom(order)
                .from(orderDetail)
                .innerJoin(orderDetail.orderId, orderAlias)
                .where(order.memberId.id.eq(memberId))
                .where(itemNameEq(requestDto.getItemName()))
                .where(dateEq(requestDto.getStartDate(), requestDto.getLastDate()))
                .where(statusEq(requestDto.getStatus()))
                .orderBy(order.orderedDate.desc())
                .fetch();

        Long total = queryFactory
                .select(order.count())
                .from(orderDetail)
                .innerJoin(orderDetail.orderId, order)
                .where(order.memberId.id.eq(memberId))
                .where(itemNameEq(requestDto.getItemName()))
                .where(dateEq(requestDto.getStartDate(), requestDto.getLastDate()))
                .where(statusEq(requestDto.getStatus()))
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
        return status != null ? order.status.eq(PaymentStatus.valueOf(status)) : null;
    }
}
