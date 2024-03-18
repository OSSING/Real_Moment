package com.project.Real_Moment.domain.repository.custom;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.presentation.dto.AdminDto;
import com.project.Real_Moment.presentation.dto.CondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminRepositoryCustom {

    Page<Admin> findAdminListByPaging(Pageable pageable, CondDto.AdminListCond dto);

    void updateByAdminInfo(Long adminId, AdminDto.AdminInfo dto);

    void updateRolesByAdminInfo(AdminDto.AdminRolesInfo dto);
}
