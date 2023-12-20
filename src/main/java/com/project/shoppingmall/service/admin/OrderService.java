package com.project.shoppingmall.service.admin;


import com.project.shoppingmall.domain.Orders;
import com.project.shoppingmall.dto.admin.response.OrderResponse;
import com.project.shoppingmall.repositroy.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;


    //모든 주문 목록 조회
    public List<OrderResponse> getOrders() {
        return ordersRepository.findAll().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    //주문 상세 조회
    public OrderResponse getOrder(Long id) {
        Orders orders = ordersRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        OrderResponse orderResponse = new OrderResponse(orders);

        return orderResponse;
    }

    //주문 상세 상태 수정
    @Transactional
    public void updateOrder(Long id, String request) {
        Orders orders = ordersRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        orders.setStatus(request);
    }
}
