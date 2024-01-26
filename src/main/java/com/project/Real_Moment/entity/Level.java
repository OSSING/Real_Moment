package com.project.Real_Moment.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;

    private String level = "WHITE";

    @Column(nullable = false, name = "reward_rate")
    private int rewardRate = 1;
}
