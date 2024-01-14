package com.project.shoppingmall.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "qa_comment")
public class QAComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_comment_id")
    private Long QACommentId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "item_qa_id")
    private ItemQA itemQA;

    private String content;

    @Column(name = "written_date")
    private LocalDateTime writtenDate;

    @Column(name = "edited_date")
    private LocalDateTime editedDate;
}
