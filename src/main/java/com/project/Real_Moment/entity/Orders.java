package com.project.Real_Moment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "ordered_date")
    private LocalDateTime orderedDate;

    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;

    @Column(nullable = false)
    private int price;

    private String name;

    private String address;

    @Column(name = "det_address")
    private String detAddress;

    private String request;

    private String tel;

    private String status;

    @Column(name = "refund_text")
    private String refundText;
}