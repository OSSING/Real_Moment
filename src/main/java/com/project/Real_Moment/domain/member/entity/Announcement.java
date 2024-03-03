package com.project.Real_Moment.domain.member.entity;


import com.project.Real_Moment.domain.admin.entity.Admin;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin adminId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean isFix = false;

    private Long viewCount;

}
