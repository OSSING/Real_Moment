package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.entity.Wish;
import com.project.Real_Moment.domain.repository.custom.WishRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long>, WishRepositoryCustom {

    boolean existsByItemIdAndMemberId(Item itemId, Member memberId);

}
