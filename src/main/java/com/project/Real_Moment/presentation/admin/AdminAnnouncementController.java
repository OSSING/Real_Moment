package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminAnnouncementService;
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
public class AdminAnnouncementController {

    private final AdminAnnouncementService adminAnnouncementService;

    @GetMapping("/admin/announcementList")
    public ResponseEntity<AnnouncementDto.AnnouncementDefWrapper> getAnnouncementList(@RequestParam("nowPage") int nowPage) {
        return ResponseEntity.ok().body(adminAnnouncementService.getAnnouncementList(nowPage));
    }
}
