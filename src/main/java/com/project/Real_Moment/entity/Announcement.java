package com.project.Real_Moment.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin adminId;

    private String title;

    private String content;

    @Column(name = "is_fix")
    private boolean isFix = false;

    @Column(name = "view_count")
    private long viewCount;

//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "modified_date")
//    private LocalDateTime modifiedDate;
}
