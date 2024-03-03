package com.project.Real_Moment.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long id;

    private String gradeName;

    private int rewardRate = 1;

    private int gradePrice;
}
