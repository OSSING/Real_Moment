package com.project.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "qa_comments")
public class QAComments {

    @Id
    @GeneratedValue
    @Column(name = "qa_comment_id")
    private Long QACommentId;

    @ManyToOne
    private Admins adminId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "qa_id")
    private QA QAId;

    private String content;

    private LocalDateTime writeDate;

    private LocalDateTime editedDate;
}
