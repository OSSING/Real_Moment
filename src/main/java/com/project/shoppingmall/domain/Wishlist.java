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
    private Long wishlistID;

    @ManyToOne
    private Users userID;

    @ManyToOne
    private Items itemID;
}