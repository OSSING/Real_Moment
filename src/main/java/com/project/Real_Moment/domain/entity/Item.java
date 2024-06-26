package com.project.Real_Moment.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends AuditingUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int price;

    private int discountRate;

    private int discountPrice;

    private int sellPrice;

    private int stock;

    @Builder.Default
    private int sellCount = 0;

    private boolean isSell;

    @Builder.Default
    private boolean isDelete = false;
    // 재고 변경 로직은 이 곳에서 구현 (Setter 말고 비즈니스 로직 메서드로 구현)
}
