package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findOneWithAuthoritiesByLoginId(String loginId);
}
