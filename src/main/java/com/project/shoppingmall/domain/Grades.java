package com.project.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Grades {

    @Id
    @GeneratedValue
    private int gradeId;

    private String grade = "WHITE";

    private int rewardRate = 1;
}
