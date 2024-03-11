package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Announcement;
import com.project.Real_Moment.domain.repository.custom.AnnouncementRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom {

}
