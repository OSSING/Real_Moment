package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.repository.custom.AnnouncementRepositoryCustom;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.member.entity.QAnnouncement.announcement;

@RequiredArgsConstructor
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public AnnouncementDto.AnnouncementListWrapper findAnnouncementListByPaging(int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);

        List<AnnouncementDto.AnnouncementList> announcementList = queryFactory
                .selectFrom(announcement)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(announcement -> new AnnouncementDto.AnnouncementList(
                        announcement.getId(),
                        announcement.getAdminId().getLoginId(),
                        announcement.getTitle(),
                        announcement.isFix(),
                        announcement.getViewCount(),
                        announcement.getCreatedDate(),
                        announcement.getLastModifiedDate()
                ))
                .toList();

        Long total = queryFactory
                .select(announcement.count())
                .from(announcement)
                .fetchOne();

        long totalPage = (long) Math.ceil((double) total / pageable.getPageSize());

        return new AnnouncementDto.AnnouncementListWrapper(announcementList, totalPage, nowPage);
    }
}
