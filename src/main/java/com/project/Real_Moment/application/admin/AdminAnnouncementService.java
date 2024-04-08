package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.Announcement;
import com.project.Real_Moment.domain.repository.AdminRepository;
import com.project.Real_Moment.domain.repository.AnnouncementRepository;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public AnnouncementDto.AnnouncementDefWrapper getAnnouncementList(int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 9);

        Page<Announcement> announcementPaging = announcementRepository.findAnnouncementListByPaging_admin(nowPage, pageable);

        List<AnnouncementDto.AnnouncementDef> announcementDto = announcementPaging.stream()
                .map(AnnouncementDto.AnnouncementDef::new)
                .toList();

        return new AnnouncementDto.AnnouncementDefWrapper(announcementDto, announcementPaging.getTotalPages(), nowPage);
    }

    @Transactional(readOnly = true)
    public AnnouncementDto.AnnouncementDef getAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항입니다."));

        return new AnnouncementDto.AnnouncementDef(announcement);
    }

    @Transactional
    public void saveAnnouncement(Long adminId, AnnouncementDto.saveAnnouncement dto) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

        announcementRepository.save(dto.toEntity(admin));
    }

    @Transactional(readOnly = true)
    public AnnouncementDto.editAnnouncementClick editAnnouncementClick(Long adminId, Long announcementId) {
        Announcement announcement = announcementValidity(adminId, announcementId);

        return new AnnouncementDto.editAnnouncementClick(announcement);
    }

    @Transactional
    public void editAnnouncement(Long adminId, AnnouncementDto.editAnnouncementClick dto) {
        announcementValidity(adminId, dto.getAnnouncementId());
        announcementRepository.updateAnnouncement(adminId, dto);
    }

    // Announcement 검증
    public void deleteAnnouncement(Long adminId, Long announcementId) {
        Announcement announcement = announcementValidity(adminId, announcementId);

        announcementRepository.delete(announcement);
    }

    private Announcement announcementValidity(Long adminId, Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElse(null);

        if (announcement != null) {
            if (!announcement.getAdminId().getId().equals(adminId)) {
                throw new IllegalArgumentException("유효하지 않은 관리자입니다.");
            }
            return announcement;
        } else {
            throw new IllegalArgumentException("존재하지 않는 공지사항입니다.");
        }
    }
}
