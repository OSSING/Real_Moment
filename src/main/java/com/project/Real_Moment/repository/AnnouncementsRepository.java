package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementsRepository extends JpaRepository<Announcement, Long> {
}
