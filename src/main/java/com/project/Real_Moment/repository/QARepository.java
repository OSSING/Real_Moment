package com.project.Real_Moment.repository;

import com.project.Real_Moment.entity.ItemQA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QARepository extends JpaRepository<ItemQA, Long> {
}
