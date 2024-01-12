package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementsRepository extends JpaRepository<Announcement, Long> {
}
