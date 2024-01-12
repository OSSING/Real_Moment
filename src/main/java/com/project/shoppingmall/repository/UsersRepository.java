package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Member, Long> {
}
