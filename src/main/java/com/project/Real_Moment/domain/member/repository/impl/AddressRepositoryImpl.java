package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Addresses;
import com.project.Real_Moment.domain.member.entity.QAddresses;
import com.project.Real_Moment.domain.member.entity.QMember;
import com.project.Real_Moment.domain.member.repository.custom.AddressRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.Real_Moment.domain.member.entity.QAddresses.addresses;
import static com.project.Real_Moment.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    @Override
//    public List<Addresses> findAddresses(Long id) {
//        return queryFactory
//                .selectFrom(addresses)
//                .where(addresses.memberId.memberId.eq(id))
//                .fetch();
//    }
}
