package com.project.Real_Moment.domain.member.entity;


import com.project.Real_Moment.domain.admin.entity.Admin;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "qa_comment")
public class QAComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_comment_id")
    private Long QACommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_qa_id")
    private ItemQA itemQA;

    private String content;

//    @Column(name = "created_date")
//    private LocalDateTime writtenDate;
//
//    @Column(name = "modified_date")
//    private LocalDateTime editedDate;
}