package com.project.Real_Moment.dto;

import com.project.Real_Moment.entity.Orders;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersListDto {

    private int price;

    private String name;

    private String address;

    private String detAddress;

    private String request;

    private String tel;

    private String status;

    private String refundText;

    // Entity -> DTO
    public OrdersListDto(Orders orders) {
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
