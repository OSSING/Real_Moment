package com.project.Real_Moment.domain.entity;


import com.project.Real_Moment.domain.enumuration.Gender;

import com.project.Real_Moment.presentation.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "loginId")})
// https://skatpdnjs.tistory.com/44
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade gradeId;

    @OneToMany(mappedBy = "memberId")
    private List<Order> orders;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "login_password")
    private String loginPassword;

    private String email;

    private String name;

    private String tel;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder.Default
    private int point = 0;

    @Builder.Default
    private int thisYearPay = 0;

    @Column(name = "recently_login")
    private LocalDateTime recentlyLogin;

    @Builder.Default
    @Column(name = "is_delete")
    private boolean isDelete = false;

    @Builder.Default
    private String roles = "ROLE_MEMBER";

    // 회원가입 시 Entity -> Dto 변환
    public MemberDto.RegisterRequest toDto() {
        return MemberDto.RegisterRequest.builder()
                .loginId(loginId)
                .email(email)
                .loginPassword(loginPassword)
                .name(name)
                .tel(tel)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }
}
