package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Grade;

import com.project.Real_Moment.domain.repository.custom.GradeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long>, GradeRepositoryCustom {

}
