package com.project.shoppingmall.repositroy;

import com.project.shoppingmall.domain.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<Admins, Long> {
}
