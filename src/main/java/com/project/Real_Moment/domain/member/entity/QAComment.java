package com.project.Real_Moment.domain.member.entity;


import com.project.Real_Moment.domain.admin.entity.Admin;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QAComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_qa_id")
    private ItemQA itemQAId;

    @Column(columnDefinition = "TEXT")
    private String content;

}
