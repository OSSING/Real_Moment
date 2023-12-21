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
    private Long CartId;

    @ManyToOne
    private Users userId;

    @ManyToOne
    private Items itemId;

    private int stock;

    private int price;

    private boolean checked;
}