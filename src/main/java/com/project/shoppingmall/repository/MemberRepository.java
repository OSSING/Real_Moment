package com.project.shoppingmall.repository;

import com.project.shoppingmall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    // 닉네임(Member - id필드) 중복 체크
    boolean existsById(String id);
}
