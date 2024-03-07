package com.project.Real_Moment.domain.member.repository.custom;

import com.project.Real_Moment.presentation.dto.AnnouncementDto;

public interface AnnouncementRepositoryCustom {

    AnnouncementDto.AnnouncementListWrapper findAnnouncementListByPaging(int nowPage);
}
