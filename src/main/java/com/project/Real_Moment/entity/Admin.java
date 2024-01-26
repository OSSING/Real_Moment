package com.project.Real_Moment.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    private String id;

    private String password;

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
