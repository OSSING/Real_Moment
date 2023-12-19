package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue
    private Long orderID;

    @ManyToOne
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
}