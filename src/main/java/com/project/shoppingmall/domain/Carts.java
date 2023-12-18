package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carts {

    @Id
    @GeneratedValue
    private Long CartID;

    @ManyToOne
    private Users userID;

    @ManyToOne
    private Items itemID;

    private int stock;

    private int price;

    private boolean checked;
}