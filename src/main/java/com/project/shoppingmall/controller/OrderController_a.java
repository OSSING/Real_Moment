package com.project.shoppingmall.controller;


import com.project.shoppingmall.dto.OrderResponse;
import com.project.shoppingmall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OrderController_a {

    @Autowired
    private OrderService orderService;

    //모든 주문 목록 조회
    @GetMapping("/admin/orders")
    public List<OrderResponse> getOrders() {
        return orderService.getOrders();
    }

    //주문 상세 조회
    @GetMapping("/admin/order/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    //주문 상세 상태 수정
    @PatchMapping("/admin/order/{id}")
    public void updateOrder(@PathVariable Long id, @RequestParam String status){
        orderService.updateOrder(id, status);
    }
}
