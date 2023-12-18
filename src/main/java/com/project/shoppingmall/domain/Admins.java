package com.project.shoppingmall.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Admins {

    @Id
    @GeneratedValue
    private Long adminID;

    @Column
    private String id;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private int grade = 1;
}
