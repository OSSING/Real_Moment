package com.project.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Announcements {

    @Id
    @GeneratedValue
    private Long noticeID;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admins adminID;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private boolean fix = false;

    @Column
    private long viewCount;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;
}
