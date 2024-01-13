package com.project.shoppingmall.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item itemId;

    private String title;

    private String content;

    @Column(nullable = false)
    private int star;

    @Column(name = "written_date")
    private LocalDateTime writtenDate;

    @Column(name = "edited_date")
    private LocalDateTime editedDate;
}
