package com.project.Real_Moment.dto;

import com.project.Real_Moment.entity.Orders;
import com.project.Real_Moment.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class OrderResponse {

    private Long orderID;

    private Member userID;

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


    public OrderResponse(Orders orders){
        orderID = orders.getOrderId();
        userID = orders.getMemberId();
        orderedDate = orders.getOrderedDate();
        sippedDate = orders.getShippedDate();
        price = orders.getPrice();
        name = orders.getName();
        address = orders.getAddress();
        detAddress = orders.getDetAddress();
        request = orders.getRequest();
        tel = orders.getTel();
        status = orders.getStatus();
        refundText = orders.getRefundText();
    }

}