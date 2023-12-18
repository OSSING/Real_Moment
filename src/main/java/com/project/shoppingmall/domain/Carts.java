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
@Table(name = "Carts")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users userID;

//    private items itemID;

    private int stock;

    private int price;

    private boolean check;
}