package com.project.Real_Moment.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_det_id")
    private Long orderDetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders ordersId;

    @Column(nullable = false, name = "fixed_price")
    private int fixedPrice;

    @Column(nullable = false, name = "discount_rate")
    private int discountRate;

    @Column(nullable = false, name = "sell_price")
    private int sellPrice;

    @Column(nullable = false)
    private int count;
}