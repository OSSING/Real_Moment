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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;

    private String name;

    private String content;

    private int price;

    private int discountRate;

    private int discountPrice;

    private int sellPrice;

    private int stock;

    private boolean isSellCheck;

    private boolean isDeleteCheck;

    private String mainImg;

    private String serveImg;

    // 재고 변경 로직은 이 곳에서 구현 (Setter 말고 비즈니스 로직 메서드로 구현)
}
