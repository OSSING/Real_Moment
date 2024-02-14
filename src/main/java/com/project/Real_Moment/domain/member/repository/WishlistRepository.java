package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
