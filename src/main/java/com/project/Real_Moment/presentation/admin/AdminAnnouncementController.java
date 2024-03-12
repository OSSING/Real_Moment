package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminAnnouncementService;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminAnnouncementController {

    private final AdminAnnouncementService adminAnnouncementService;

    @GetMapping("/admin/announcementList")
    public ResponseEntity<AnnouncementDto.AnnouncementDefWrapper> getAnnouncementList(@RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok().body(adminAnnouncementService.getAnnouncementList(nowPage));
    }

    @GetMapping("/admin/announcement")
    public ResponseEntity<AnnouncementDto.AnnouncementDef> getAnnouncement(@RequestParam("announcementId") Long announcementId) {
        return ResponseEntity.ok().body(adminAnnouncementService.getAnnouncement(announcementId));
    }

    @PostMapping("/admin/{id}/announcement")
    public ResponseEntity<Void> saveAnnouncement(@PathVariable("id") Long id, @RequestBody AnnouncementDto.saveAnnouncement dto) {
        adminAnnouncementService.saveAnnouncement(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/{id}/announcement/data")
    public ResponseEntity<AnnouncementDto.editAnnouncementClick> editAnnouncementClick(@PathVariable("id") Long id, @RequestParam("announcementId") Long announcementId) {
        return ResponseEntity.ok().body(adminAnnouncementService.editAnnouncementClick(id, announcementId));
    }
}
