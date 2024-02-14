package com.project.Real_Moment.domain.member.repository;

import com.project.Real_Moment.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    // 닉네임(Member - id필드) 중복 체크
    boolean existsById(String id);

    Optional<Member> findOneWithAuthoritiesById(String id);
}
