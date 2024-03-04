package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Item;
import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.entity.Wish;
import com.project.Real_Moment.domain.member.repository.custom.WishRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long>, WishRepositoryCustom {

    boolean existsByItemIdAndMemberId(Item itemId, Member memberId);

}
