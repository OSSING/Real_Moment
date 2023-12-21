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
    @GeneratedValue
    private Long addressId;

    @ManyToOne
    private Users userId;

    private String name;

    private String address;

    private String detAddress;

    private boolean isDefAddress = false;
}