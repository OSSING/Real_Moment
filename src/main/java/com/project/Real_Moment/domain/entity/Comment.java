package com.project.Real_Moment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_on_one_id")
    private OneOnOne oneOnOneId;

    @Column(columnDefinition = "TEXT")
    private String content;
}
