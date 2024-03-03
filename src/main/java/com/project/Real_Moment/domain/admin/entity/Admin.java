package com.project.Real_Moment.domain.admin.entity;


import com.project.Real_Moment.domain.member.entity.Authority;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "loginId")})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String loginId;

    private String loginPassword;

    private String email;

    private String name;

    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "admin_authority",
            joinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "admin_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
