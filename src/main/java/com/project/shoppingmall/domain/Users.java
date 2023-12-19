package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue
    private Long userID;

    @OneToOne
    private Grades gradeID;

    private String id;

    private String email;

    private String password;

    private String name;

    private String tel;

    private Date birthDate;

    private char gender;

    private int point;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private LocalDateTime recentlyLogin;

    private boolean status;

    private boolean deleted;

    private boolean loginStatus = false;
}
