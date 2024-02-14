package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepository extends JpaRepository<Level, Integer> {
}
