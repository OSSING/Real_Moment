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
    private Long itemID;

    @ManyToOne
    private Category category;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    private int price;

    @Column
    private int discountRate;

    @Column
    private int sellPrice;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column
    private int stock;

    @Column
    private boolean sellCheck;

    @Column
    private boolean deleteCheck;

    @Column
    private String mainImg;

    @Column
    private String serveImg;
}
