package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wishlist {

    @Id
    @GeneratedValue
    private Long wishlistId;

    @ManyToOne
    private Users userId;

    @ManyToOne
    private Items itemId;
}