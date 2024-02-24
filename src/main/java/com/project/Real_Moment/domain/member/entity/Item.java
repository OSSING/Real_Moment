package com.project.Real_Moment.domain.member.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;

    private String name;

    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "discount_rate")
    private int discountRate;

    @Column(nullable = false, name = "sell_price")
    private int sellPrice;

//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "modified_date")
//    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private int stock;

    @Column(name = "is_sell_check")
    private boolean isSellCheck;

    @Column(name = "is_delete_check")
    private boolean isDeleteCheck;

    @Column(name = "main_img")
    private String mainImg;

    @Column(name = "serve_img")
    private String serveImg;

    // 재고 변경 로직은 이 곳에서 구현 (Setter 말고 비즈니스 로직 메서드로 구현)
}