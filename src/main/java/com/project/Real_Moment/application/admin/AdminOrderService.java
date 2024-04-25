package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.Order;
import com.project.Real_Moment.domain.entity.OrderDetail;
import com.project.Real_Moment.domain.repository.ItemRepository;
import com.project.Real_Moment.domain.repository.OrderDetailRepository;
import com.project.Real_Moment.domain.repository.OrderRepository;
import com.project.Real_Moment.domain.repository.S3FileRepository;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.ItemDto;
import com.project.Real_Moment.presentation.dto.OrderDetailDto;
import com.project.Real_Moment.presentation.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;
    private final S3FileRepository s3FileRepository;

    @Transactional(readOnly = true)
    public OrderDto.OrderListPaging getOrderList(CondDto.AdminOrderListCond condDto) {
        Pageable pageable = PageRequest.of(condDto.getNowPage() - 1, 9);

        Page<Order> orderListPaging = orderRepository.findByOrderListPage_Admin(condDto, pageable);

        List<OrderDto.OrderList> orderListDto = orderListPaging.stream()
                .map(OrderDto.OrderList::new)
                .toList();

        for (OrderDto.OrderList orderDto : orderListDto) {
            Order order = getOrderEntity(orderDto.getOrderId());
            orderDto.setOrderDetails(getOrderDetailListDto(order, orderDto.getOrderId()));
        }

        return new OrderDto.OrderListPaging(orderListDto, orderListPaging.getTotalPages(), condDto.getNowPage());
    }

    @Transactional(readOnly = true)
    public OrderDto.OrderDet getOrderDet(Long orderId) {

        Order order = getOrderEntity(orderId);

        OrderDto.OrderList orderById = new OrderDto.OrderList(order, getOrderDetailListDto(order, orderId));

        return new OrderDto.OrderDet(order, orderById);
    }

    private List<OrderDetailDto.OrderDetailList> getOrderDetailListDto(Order order, Long orderId) {

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order);

        return orderDetailList.stream()
                .map(orderDetail -> {
                    Item item = itemRepository.findById(orderDetail.getItemId().getId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

                    // 상품의 메인 이미지 가져오기
                    String mainImgList = s3FileRepository.findMainImg_UrlByItemId(item);

                    ItemDto.OrderedItemList orderedItemList = new ItemDto.OrderedItemList(item, mainImgList);

                    return new OrderDetailDto.OrderDetailList(orderDetail, orderedItemList);
                })
                .toList();
    }

    private Order getOrderEntity(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("존재하는 주문이 아닙니다."));
    }

    @Transactional
    public void updateOrderStatus(OrderDto.UpdateOrderStatus dto) {
        orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        orderRepository.updateOrderStatus(dto);
    }
}
