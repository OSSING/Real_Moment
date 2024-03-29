package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OrderDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemList {
        private List<OrderItem> orderItems;
        private OrderPrice orderPrice;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderPrice {
        private int totalPrice;
        private int totalDiscountPrice;
        private int totalSellPrice;
        private int point;
        private int getPoint;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private Boolean isSell;
        private List<ItemDto.MainImgListResponse> mainImg;
        private int count;
        private int totalSellPrice;

        public OrderItem(Item item, int count, List<ItemDto.MainImgListResponse> mainImgUrl) {
            itemId = item.getId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            isSell = item.isSell();
            mainImg = mainImgUrl;
            this.count = count;
            this.totalSellPrice = item.getSellPrice() * count;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemListRequest {
        private List<OrderItemRequest> requestList;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequest {
        private Long itemId;
        private int count;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentFirst {
        private String name;
        private String tel;
        private String mainAddress;
        private String detAddress;
        private String requestText;
        private List<OrderItemRequest> items;
        private int totalPrice;
        private int totalDiscountPrice;
        private int usePoint;
        private int getPoint;
        private int buyPrice;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentResponse {
        private String merchantUid;
        private String itemName;
        private int paymentPrice;
        private String buyerName;
        private String buyerEmail;
        private String buyerAddress;
    }
}
