package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementsRepository extends JpaRepository<Announcement, Long> {
}
