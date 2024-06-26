package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.Order;
import com.project.Real_Moment.domain.enumuration.PaymentStatus;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
        private String mainImg;
        private int count;
        private int totalSellPrice;

        public OrderItem(Item item, int count, String mainImgUrl) {
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

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentSecond {
        private String impUid;
        private String merchantUid;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderListPaging {
        private List<OrderList> orderList;
        private int totalPage;
        private int nowPage;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDet {
        private OrderList order;
        private int totalPrice;
        private int totalDiscountPrice;
        private int usePoint;
        private int getPoint;

        public OrderDet(Order findOrder, OrderDto.OrderList orderList) {
            order = orderList;
            totalPrice = findOrder.getTotalPrice();
            totalDiscountPrice = findOrder.getTotalDiscountPrice();
            usePoint = findOrder.getUsePoint();
            getPoint = findOrder.getGetPoint();
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderList {
        private Long orderId;
        private LocalDateTime orderedDate;
        private int buyPrice;
        private String mainAddress;
        private String detAddress;
        private String requestText;
        private String tel;
        private String status;
        private String merchantUid;
        private List<OrderDetailDto.OrderDetailList> orderDetails;

        public OrderList(Order order) {
            orderId = order.getId();
            orderedDate = order.getOrderedDate();
            buyPrice = order.getBuyPrice();
            mainAddress = order.getMainAddress();
            detAddress = order.getDetAddress();
            requestText = order.getRequestText();
            tel = order.getTel();
            status = PaymentStatus.getDescription(String.valueOf(order.getStatus()));
            merchantUid = order.getMerchantUid();
        }

        public OrderList(Order order, List<OrderDetailDto.OrderDetailList> orderDetailListDto) {
            orderId = order.getId();
            orderedDate = order.getOrderedDate();
            buyPrice = order.getBuyPrice();
            mainAddress = order.getMainAddress();
            detAddress = order.getDetAddress();
            requestText = order.getRequestText();
            tel = order.getTel();
            status = PaymentStatus.getDescription(String.valueOf(order.getStatus()));
            merchantUid = order.getMerchantUid();
            orderDetails = orderDetailListDto;
        }
    }

//    @Getter @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class OrderById {
//        private Long orderId;
//        private LocalDateTime orderedDate;
//        private int buyPrice;
//        private String mainAddress;
//        private String detAddress;
//        private String requestText;
//        private String tel;
//        private String status;
//        private String merchantUid;
//        private List<OrderDetailDto.OrderDetailList> orderDetails;
//
//        public OrderById(Order order, List<OrderDetailDto.OrderDetailList> orderDetailListDto) {
//            orderId = order.getId();
//            orderedDate = order.getOrderedDate();
//            buyPrice = order.getBuyPrice();
//            mainAddress = order.getMainAddress();
//            detAddress = order.getDetAddress();
//            requestText = order.getRequestText();
//            tel = order.getTel();
//            status = PaymentStatus.getDescription(String.valueOf(order.getStatus()));
//            merchantUid = order.getMerchantUid();
//            orderDetails = orderDetailListDto;
//        }
//    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderCancelRequest {
        private Long orderId;
        private String reasonText;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderId {
        private Long orderId;
    }
}
