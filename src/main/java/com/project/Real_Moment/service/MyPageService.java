package com.project.Real_Moment.service;

import com.project.Real_Moment.dto.OrdersListDto;
import com.project.Real_Moment.entity.Orders;
import com.project.Real_Moment.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    public List<OrdersListDto> findOrdersList(Long id) {

        List<Orders> ordersList = ordersRepository.findByMemberId_MemberId(id);

        return ordersList.stream()
                .map(OrdersListDto::new).toList();
    }
}
