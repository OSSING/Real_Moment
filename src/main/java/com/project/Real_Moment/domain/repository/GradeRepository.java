package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Grade;
<<<<<<< HEAD
=======

>>>>>>> 1d1a7541923abcc6710a8ea613f7c1e063f1ef2f
import com.project.Real_Moment.domain.repository.custom.GradeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long>, GradeRepositoryCustom {

}
