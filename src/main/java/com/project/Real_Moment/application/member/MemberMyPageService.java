package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.entity.Orders;
import com.project.Real_Moment.domain.member.repository.OrdersRepository;
import com.project.Real_Moment.presentation.dto.MeberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberMyPageService {

    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    public List<MeberDto.OrdersListDto> findOrdersList(Long id) {

        List<Orders> ordersList = ordersRepository.findByMemberId_MemberId(id);

        return ordersList.stream()
                .map(MeberDto.OrdersListDto::new).toList();
    }
}
