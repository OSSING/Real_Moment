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
@Table(name = "item_qa")
public class ItemQA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_qa_id")
    private Long itemQAId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @Column(name = "written_date")
    private LocalDateTime writtenDate;

    @Column(name = "edited_date")
    private LocalDateTime editedDate;

}
