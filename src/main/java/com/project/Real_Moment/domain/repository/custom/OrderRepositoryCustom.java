package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Order;
import com.project.Real_Moment.presentation.dto.CondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    void updatePaymentComplete(Long orderId);

    Page<Order> findByOrderListPage(Long memberId, CondDto.OrderListCond requestDto, Pageable pageable);

    void updatePaymentCancel(Long orderId);

    void updatePaymentRefundRequest(Long orderId);

    void updateReasonText(Long orderId, String reasonText);

    void updatePaymentDone(Long orderId);

}
