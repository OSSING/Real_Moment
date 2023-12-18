package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "userID")
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