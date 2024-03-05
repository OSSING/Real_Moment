package com.project.Real_Moment.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    private LocalDateTime orderedDate;

    private LocalDateTime deliveryDate;

    private int totalPrice;

    private int totalDiscountPrice;

    private int usePoint;

    private int getPoint;

    private int buyPrice;

    private String name;

    private String mainAddress;

    private String detAddress;

    private String requestText;

    private String tel;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String reasonText;

    private String merchantUid;

    private String impUid;
}