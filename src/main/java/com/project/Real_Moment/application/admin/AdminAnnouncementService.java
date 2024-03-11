package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Announcement;
import com.project.Real_Moment.domain.repository.AnnouncementRepository;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementDto.AnnouncementDefWrapper getAnnouncementList(int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);

        Page<Announcement> announcementPaging = announcementRepository.findAnnouncementListByPaging_admin(nowPage, pageable);

        List<AnnouncementDto.AnnouncementDef> announcementDto = announcementPaging.stream()
                .map(AnnouncementDto.AnnouncementDef::new)
                .toList();

        return new AnnouncementDto.AnnouncementDefWrapper(announcementDto, announcementPaging.getTotalPages(), nowPage);
    }
}
