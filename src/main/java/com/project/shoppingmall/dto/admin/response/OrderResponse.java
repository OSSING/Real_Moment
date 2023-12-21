package com.project.shoppingmall.dto.admin.response;

import com.project.shoppingmall.domain.Orders;
import com.project.shoppingmall.domain.Users;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
@AllArgsConstructor
public class OrderResponse {

    private Long orderID;

    private Users userID;

    private LocalDateTime orderedDate;

    private LocalDateTime sippedDate;

    private int price;

    private String name;

    private String address;

    private String detAddress;

    private String request;

    private String tel;

    private String status;

    private String refundText;


    public OrderResponse(Orders order){
        orderID = order.getOrderId();
        userID = order.getUserId();
        orderedDate = order.getOrderedDate();
        sippedDate = order.getSippedDate();
        price = order.getPrice();
        name = order.getName();
        address = order.getAddress();
        detAddress = order.getDetAddress();
        request = order.getRequest();
        tel = order.getTel();
        status = order.getStatus();
        refundText = order.getRefundText();
    }

}
