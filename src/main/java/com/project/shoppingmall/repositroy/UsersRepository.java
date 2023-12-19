package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
