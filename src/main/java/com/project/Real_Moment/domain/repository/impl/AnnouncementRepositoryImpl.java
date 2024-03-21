package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Announcement;
import com.project.Real_Moment.domain.repository.custom.AnnouncementRepositoryCustom;
import com.project.Real_Moment.presentation.dto.AnnouncementDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QAnnouncement.announcement;
import static com.project.Real_Moment.domain.entity.QReview.review;

@RequiredArgsConstructor
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public AnnouncementDto.AnnouncementListWrapper findAnnouncementListByPaging(int nowPage) {
        Pageable pageable = PageRequest.of(nowPage - 1, 10);

        List<AnnouncementDto.AnnouncementList> announcementList = queryFactory
                .selectFrom(announcement)
                .orderBy(announcement.createdDate.desc().nullsLast())
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

    @Override
    public Page<Announcement> findAnnouncementListByPaging_admin(int nowPage, Pageable pageable) {

        List<Announcement> announcementList = queryFactory
                .selectFrom(announcement)
                .orderBy(announcement.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(announcement.count())
                .from(announcement)
                .fetchOne();

        return new PageImpl<>(announcementList, pageable, total);
    }

    @Override
    public void updateAnnouncement(Long adminId, AnnouncementDto.editAnnouncementClick dto) {
        queryFactory
                .update(announcement)
                .set(announcement.title, dto.getTitle())
                .set(announcement.content, dto.getContent())
                .set(announcement.isFix, dto.getIsFix())
                .where(announcement.adminId.id.eq(adminId),
                        announcement.id.eq(dto.getAnnouncementId()))
                .execute();
    }
}
