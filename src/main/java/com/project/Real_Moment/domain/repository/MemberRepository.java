package com.project.Real_Moment.domain.repository;


import com.project.Real_Moment.domain.entity.Grade;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {


    // 닉네임(Member - id필드) 중복 체크
    boolean existsByLoginId(String loginId);

    Optional<Member> findOneWithAuthoritiesByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);


    List<Member> findMemberListByGradeId(Grade grade);
}
