package com.project.Real_Moment.domain.entity;

import com.project.Real_Moment.domain.enumuration.AdminAuthority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "loginId")})
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String loginId;

    private String loginPassword;

    private String email;

    private String name;

    private boolean isDelete = false;

    @Enumerated(EnumType.STRING)
    private AdminAuthority roles;

//    @ManyToMany
//    @JoinTable(
//            name = "admin_authority",
//            joinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<Authority> authorities;
}
