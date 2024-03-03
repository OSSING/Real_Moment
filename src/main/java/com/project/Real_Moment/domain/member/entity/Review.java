package com.project.Real_Moment.domain.member.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item itemId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int star;
}
