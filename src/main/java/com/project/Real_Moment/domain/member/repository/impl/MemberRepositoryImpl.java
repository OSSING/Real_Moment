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

//    @Override
//    public Member updateTelById(Long id, String tel) {
//        queryFactory
//                .update(member)
//                .set(member.tel, tel)
//                .where(member.id.eq(id))
//                .execute();
//
//        return queryFactory
//                .selectFrom(member)
//                .where(member.id.eq(id))
//                .fetchOne();
//    }

    @Override
    public Long updateActivatedById(Long id) {
        return queryFactory
                .update(member)
                .set(member.isDelete, true)
                .set(member.email, "")
                .set(member.loginPassword, "")
                .set(member.name, "")
                .set(member.tel, "")
                .set(member.birthDate, (LocalDate) null)
                .set(member.gender, 'N')
                .where(member.id.eq(id))
                .execute();
    }
}