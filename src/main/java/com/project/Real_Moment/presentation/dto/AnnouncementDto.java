package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnnouncementListWrapper {
        private List<AnnouncementList> announcementList;
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
}
