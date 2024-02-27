package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Addresses;
import com.project.Real_Moment.domain.member.repository.custom.AddressesRepositoryCustom;
import com.project.Real_Moment.presentation.dto.AddressesDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.member.entity.QAddresses.addresses;


@RequiredArgsConstructor
public class AddressesRepositoryImpl implements AddressesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Addresses shivar(AddressesDto.AddressRequest dto) {
        queryFactory
                .update(addresses)
                .set(addresses.name, dto.getName())
                .set(addresses.address, dto.getAddress())
                .set(addresses.detAddress, dto.getDetAddress())
                .set(addresses.isDefAddress, dto.getIsDefAddress())
                .where(addresses.addressId.eq(dto.getAddressId()))
                .execute();

        return queryFactory
                .selectFrom(addresses)
                .where(addresses.addressId.eq(dto.getAddressId()))
                .fetchOne();
    }
}
