package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepository extends JpaRepository<Level, Integer> {
}
