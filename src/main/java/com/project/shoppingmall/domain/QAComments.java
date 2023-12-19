package com.project.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QAComments {

    @Id
    @GeneratedValue
    private Long QACommentID;

    @ManyToOne
    private Admins adminID;

    @OneToOne
    private QA QAID;

    private String content;

    private LocalDateTime writeDate;

    private LocalDateTime editedDate;
}
