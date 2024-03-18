package com.project.Real_Moment.domain.repository.impl;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.entity.AdminAuthority;
import com.project.Real_Moment.domain.repository.custom.AdminRepositoryCustom;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.Real_Moment.domain.entity.QAdmin.admin;

@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Admin> findAdminListByPaging(Pageable pageable, CondDto.AdminListCond dto) {
        List<Admin> adminList = queryFactory
                .selectFrom(admin)
                .where(loginIdEq(dto.getLoginId()),
                        nameEq(dto.getName()),
                        rolesEq(dto.getRoles()))
                .orderBy(admin.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(admin.count())
                .from(admin)
                .where(loginIdEq(dto.getLoginId()),
                        nameEq(dto.getName()),
                        rolesEq(dto.getRoles()))
                .fetchOne();

        return new PageImpl<>(adminList, pageable, total);
    }

    private BooleanExpression loginIdEq(String loginId) {
        return loginId != null ? admin.loginId.eq(loginId) : null;
    }

    private BooleanExpression nameEq(String name) {
        return name != null ? admin.name.eq(name) : null;
    }

    private BooleanExpression rolesEq(String roles) {
        return roles != null ? admin.roles.eq(AdminAuthority.valueOf(roles)) : null;
    }
}
