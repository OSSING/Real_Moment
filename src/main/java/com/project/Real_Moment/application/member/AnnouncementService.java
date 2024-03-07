package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.member.repository.AnnouncementRepository;
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

    @Transactional
    public AnnouncementDto.AnnouncementListWrapper getAnnouncementList(int nowPage) {
        return announcementRepository.findAnnouncementListByPaging(nowPage);
    }
}
