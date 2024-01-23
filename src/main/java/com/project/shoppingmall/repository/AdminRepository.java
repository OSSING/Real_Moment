package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @EntityGraph(attributePaths = "authorities") // admin필드의 authorities를 함께 가져옴
    Optional<Admin> findOneWithAuthoritiesById(String id);
}
