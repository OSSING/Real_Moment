package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, Long> {
}
