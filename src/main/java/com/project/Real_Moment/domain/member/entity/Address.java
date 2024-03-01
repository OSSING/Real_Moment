package com.project.Real_Moment.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    private String name;

    private String tel;

    @Column(name = "main_address")
    private String mainAddress;

    @Column(name = "det_address")
    private String detAddress;

    @Builder.Default
    @Column(name = "is_def_address")
    private Boolean isDefAddress = false;
}