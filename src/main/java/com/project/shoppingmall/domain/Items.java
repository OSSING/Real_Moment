package com.project.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Items {

    @Id
    @GeneratedValue
    private Long itemId;

    @ManyToOne
    private Category category;

    private String name;

    private String content;

    private int price;

    private int discountRate;

    private int sellPrice;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private int stock;

    private boolean sellCheck;

    private boolean deleteCheck;

    private String mainImg;

    private String serveImg;
}
