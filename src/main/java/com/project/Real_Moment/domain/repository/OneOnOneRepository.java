package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.OneOnOne;
import com.project.Real_Moment.domain.repository.custom.OneOnOneRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneOnOneRepository extends JpaRepository<OneOnOne, Long>, OneOnOneRepositoryCustom {
}
