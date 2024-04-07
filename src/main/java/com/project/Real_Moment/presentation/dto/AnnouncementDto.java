package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnnouncementListWrapper {
        private List<AnnouncementDto.AnnouncementList> announcementList;
        private long totalPage;
        private long nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnnouncementList {
        private Long announcementId;
        private String adminName;
        private String title;
        private Boolean isFix;
        private Long viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public AnnouncementList(Announcement announcement) {
            announcementId = announcement.getId();
            adminName = announcement.getAdminId().getLoginId();
            title = announcement.getTitle();
            isFix = announcement.isFix();
            viewCount = announcement.getViewCount();
            createdDate = announcement.getCreatedDate();
            lastModifiedDate = announcement.getLastModifiedDate();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnnouncementDefWrapper {
        private List<AnnouncementDto.AnnouncementDef> announcementList;
        private long totalPage;
        private long nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnnouncementDef {
        private Long announcementId;
        private String adminName;
        private String title;
        private String content;
        private boolean isFix;
        private Long viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public AnnouncementDef(Announcement announcement) {
            announcementId = announcement.getId();
            adminName = announcement.getAdminId().getLoginId();
            title = announcement.getTitle();
            content = announcement.getContent();
            isFix = announcement.isFix();
            viewCount = announcement.getViewCount() + 1;
            createdDate = announcement.getCreatedDate();
            lastModifiedDate = announcement.getLastModifiedDate();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class saveAnnouncement {
        private String title;
        private String content;
        private Boolean isFix;

        public Announcement toEntity(Admin admin) {
            return Announcement.builder()
                    .adminId(admin)
                    .title(title)
                    .content(content)
                    .isFix(isFix)
                    .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class editAnnouncementClick {
        private Long announcementId;
        private String title;
        private String content;
        private Boolean isFix;

        public editAnnouncementClick(Announcement announcement) {
            announcementId = announcement.getId();
            title = announcement.getTitle();
            content = announcement.getContent();
            isFix = announcement.isFix();
        }
    }
}
