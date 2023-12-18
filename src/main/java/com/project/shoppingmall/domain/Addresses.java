package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Addresses")
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users userID;

    private String name;

    private String address;

    private String detAddress;

    private boolean isDefAddress = false;
}