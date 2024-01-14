package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepository extends JpaRepository<Level, Integer> {
}
