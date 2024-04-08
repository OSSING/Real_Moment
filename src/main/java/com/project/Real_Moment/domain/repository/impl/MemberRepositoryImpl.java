package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.enumuration.Gender;
import com.project.Real_Moment.domain.entity.Member;
import com.project.Real_Moment.domain.repository.custom.MemberRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.project.Real_Moment.domain.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateRecentlyLogin(String loginId) {
        queryFactory
                .update(member)
                .set(member.recentlyLogin, LocalDateTime.now())
                .where(member.loginId.eq(loginId))
                .execute();
    }

    @Override
    public Member updatePasswordById(Long id, String password) {
        queryFactory
                .update(member)
                .set(member.loginPassword, password)
                .where(member.id.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public Member updateEmailById(Long id, String email) {
        queryFactory
                .update(member)
                .set(member.email, email)
                .where(member.id.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public Member updateNameById(Long id, String name) {
        queryFactory
                .update(member)
                .set(member.name, name)
                .where(member.id.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public Member updateBirthDateById(Long id, LocalDate birthDate) {
        queryFactory
                .update(member)
                .set(member.birthDate, birthDate)
                .where(member.id.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public Long updateActivatedById(Long memberId) {
        return queryFactory
                .update(member)
                .set(member.isDelete, true)
                .set(member.email, (String) null)
                .set(member.loginPassword, (String) null)
                .set(member.name, (String) null)
                .set(member.tel, (String) null)
                .set(member.birthDate, (LocalDate) null)
                .set(member.gender, (Gender) null)
                .where(member.id.eq(memberId))
                .execute();
    }

    @Override
    public Page<Member> findMemberListByCond(Pageable pageable, CondDto.MemberListCond dto) {
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .where(loginIdEq(dto.getLoginId()),
                        gradeEq(dto.getGradeId()),
                        isDeleteEq(dto.getIsDelete()))
                .orderBy(memberSort(dto.getMemberSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(member.count())
                .from(member)
                .where(loginIdEq(dto.getLoginId()),
                        gradeEq(dto.getGradeId()),
                        isDeleteEq(dto.getIsDelete()))
                .fetchOne();

        return new PageImpl<>(memberList, pageable, total);
    }

    private OrderSpecifier<?> memberSort(String memberSort) {
        if (memberSort == null || memberSort.equals("day")) {
            return member.createdDate.desc().nullsLast();
        } else {
            if (memberSort.equals("totalPay")) {
                return member.thisYearPay.desc();
            }
        }
        return null;
    }

    private BooleanExpression loginIdEq(String loginId) {
        return loginId != null ? member.loginId.eq(loginId) : null;
    }

    private BooleanExpression gradeEq(Long gradeId) {
        return gradeId != null ? member.gradeId.id.eq(gradeId) : null;
    }

    private BooleanExpression isDeleteEq(Boolean isDelete) {
        return isDelete != null ? member.isDelete.eq(isDelete) : null;
    }
}