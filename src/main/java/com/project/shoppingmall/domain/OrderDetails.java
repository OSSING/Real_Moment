package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetID;

//    private Long itemID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Orders orderID;

    private int fixedPrice;

    private int discountRate;

    private int sellPrice;

    private int count;
}