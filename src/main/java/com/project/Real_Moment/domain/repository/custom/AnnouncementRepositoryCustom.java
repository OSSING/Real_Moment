package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Announcement;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementRepositoryCustom {

    AnnouncementDto.AnnouncementListWrapper findAnnouncementListByPaging(int nowPage);

    Page<Announcement> findAnnouncementListByPaging_admin(int nowPage, Pageable pageable);
}
