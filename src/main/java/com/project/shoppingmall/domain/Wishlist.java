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
public class Wishlist {

    @Id
    @GeneratedValue
    private Long wishlistID;

    @ManyToOne
    private Users userID;

//    private Long itemID;
}