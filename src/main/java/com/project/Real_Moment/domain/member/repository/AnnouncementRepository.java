package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Announcement;
import com.project.Real_Moment.domain.member.repository.custom.AnnouncementRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom {

}
