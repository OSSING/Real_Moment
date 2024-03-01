package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.domain.member.repository.custom.AddressRepositoryCustom;
import com.project.Real_Moment.presentation.dto.AddressDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.Real_Moment.domain.member.entity.QAddress.address;


@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateAddress(AddressDto.AddressRequest dto) {
        queryFactory
                .update(address)
                .set(address.name, dto.getName())
                .set(address.tel, dto.getTel())
                .set(address.mainAddress, dto.getMainAddress())
                .set(address.detAddress, dto.getDetAddress())
                .set(address.isDefAddress, dto.getIsDefAddress())
                .where(address.addressId.eq(dto.getAddressId()))
                .execute();
    }

}

