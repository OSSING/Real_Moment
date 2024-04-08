package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.application.member.AnnouncementService;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping("/announcementList")
    public ResponseEntity<AnnouncementDto.AnnouncementListWrapper> getAnnouncementList(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage) {
        return ResponseEntity.ok().body(announcementService.getAnnouncementList(nowPage));
    }

    @GetMapping("/announcement")
    public ResponseEntity<AnnouncementDto.AnnouncementDef> getAnnouncement(@RequestParam("announcementId") Long announcementId) {
        return ResponseEntity.ok().body(announcementService.getAnnouncement(announcementId));
    }
}
