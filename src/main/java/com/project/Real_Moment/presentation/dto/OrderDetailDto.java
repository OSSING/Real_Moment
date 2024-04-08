package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OrderDetailDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailList {
        private Long orderDetailId;
        private ItemDto.OrderedItemList item;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int itemCount;
        private int totalPrice;

        public OrderDetailList(OrderDetail orderDetail, ItemDto.OrderedItemList orderedItemList) {
            orderDetailId = orderDetail.getId();
            item = orderedItemList;
            price = orderDetail.getPrice();
            discountRate = orderDetail.getDiscountRate();
            discountPrice = orderDetail.getDiscountPrice();
            sellPrice = orderDetail.getSellPrice();
            itemCount = orderDetail.getItemCount();
            totalPrice = orderDetail.getTotalPrice();
        }
    }
}
