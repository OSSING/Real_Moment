package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
