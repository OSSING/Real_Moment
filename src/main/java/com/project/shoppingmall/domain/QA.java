package com.project.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "qa")
public class QA {

    @Id
    @GeneratedValue
    @Column(name = "qa-id")
    private Long QAId;

    @ManyToOne
    private Items itemId;

    @ManyToOne
    private Users userId;

    private String title;

    private String content;

    private LocalDateTime writeDate;

    private LocalDateTime editedDate;
}
