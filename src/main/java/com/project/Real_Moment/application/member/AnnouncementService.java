package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.repository.AnnouncementRepository;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public AnnouncementDto.AnnouncementListWrapper getAnnouncementList(int nowPage) {
        return announcementRepository.findAnnouncementListByPaging(nowPage);
    }

    @Transactional(readOnly = true)
    public AnnouncementDto.AnnouncementDef getAnnouncement(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .map(AnnouncementDto.AnnouncementDef::new)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 공지사항이 아닙니다."));
    }
}
