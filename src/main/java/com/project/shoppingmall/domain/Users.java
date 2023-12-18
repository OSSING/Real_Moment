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
    // 기본키 생성을 MySQL에 위임해서 AUTO_INCREMEMT를 통해 칼럼이 생성될 때마다 ID값을 1씩 자동으로 올림
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
