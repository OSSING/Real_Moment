package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Member;
import com.project.Real_Moment.domain.member.repository.custom.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.project.Real_Moment.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member updatePasswordById(Long id, String password) {
        queryFactory
                .update(member)
                .set(member.password, password)
                .where(member.memberId.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.memberId.eq(id))
                .fetchOne();
    }

    @Override
    public Member updateEmailById(Long id, String email) {
        queryFactory
                .update(member)
                .set(member.email, email)
                .where(member.memberId.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.memberId.eq(id))
                .fetchOne();
    }

    @Override
    public Member updateNameById(Long id, String name) {
        queryFactory
                .update(member)
                .set(member.name, name)
                .where(member.memberId.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.memberId.eq(id))
                .fetchOne();
    }

    @Override
    public Member updateBirthDateById(Long id, LocalDate birthDate) {
        queryFactory
                .update(member)
                .set(member.birthDate, birthDate)
                .where(member.memberId.eq(id))
                .execute();

        return queryFactory
                .selectFrom(member)
                .where(member.memberId.eq(id))
                .fetchOne();
    }
}