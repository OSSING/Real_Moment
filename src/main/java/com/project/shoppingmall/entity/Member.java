package com.project.shoppingmall.entity;

import com.project.shoppingmall.dto.RegisterDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level levelId;

    private String id;

    private String email;

    private String password;

    private String name;

    private String tel;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(nullable = false)
    private char gender;

    @Column(nullable = false)
    private int point;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "recently_login")
    private LocalDateTime recentlyLogin;

    @Column(name = "is_member_status")
    private boolean isMemberStatus;

    @Column(name = "is_login_status")
    private boolean isLoginStatus = false;
}
