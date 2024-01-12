package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    private String name;

    private String address;

    @Column(name = "det_address")
    private String detAddress;

    @Column(name = "is_def_address")
    private boolean isDefAddress = false;
}