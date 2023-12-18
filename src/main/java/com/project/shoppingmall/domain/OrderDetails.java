package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue
    private Long orderDetID;

    @ManyToOne
    private Items itemID;

    @ManyToOne
    private Orders orderID;

    private int fixedPrice;

    private int discountRate;

    private int sellPrice;

    private int count;
}