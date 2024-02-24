package com.project.Real_Moment.domain.member.entity;

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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
// https://skatpdnjs.tistory.com/44
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level levelId;

    @OneToMany(mappedBy = "memberId")
    private List<Orders> orders;

    private String id;

    private String email;

    private String password;

    private String name;

    private String tel;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false)
    private char gender;

    @Builder.Default
    @Column(nullable = false)
    private int point = 0;

    @Column(name = "recently_login")
    private LocalDateTime recentlyLogin;

    @Column(name = "is_member_status")
    private boolean isMemberStatus;

    @Builder.Default
    @Column(name = "member_role")
    private String memberRole = "ROLE_MEMBER";

    private boolean activated;

    // 회원가입 시 Entity -> Dto 변환
    public MemberDto.RegisterRequest toDto() {
        return MemberDto.RegisterRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .tel(tel)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }
}