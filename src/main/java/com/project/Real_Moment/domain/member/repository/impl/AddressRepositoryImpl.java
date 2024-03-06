package com.project.Real_Moment.domain.member.repository.impl;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.domain.member.repository.custom.AddressRepositoryCustom;
import com.project.Real_Moment.presentation.dto.AddressDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
                .where(address.id.eq(dto.getAddressId()))
                .execute();
    }

    @Override
    public Page<Address> findAddressByPaging(Long memberId, Pageable pageable) {

        List<Address> addressList = queryFactory
                .selectFrom(address)
                .where(address.memberId.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(address.count())
                .from(address)
                .where(address.memberId.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(addressList, pageable, total);
    }
}

