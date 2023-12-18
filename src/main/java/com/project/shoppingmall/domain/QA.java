package com.project.shoppingmall.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QA {

    @Id
    @GeneratedValue
    private Long QAID;

    @ManyToOne
    private Items itemID;

    @ManyToOne
    private Users userID;

    private String title;

    private String content;

    private LocalDateTime writeDate;

    private LocalDateTime editedDate;
}
