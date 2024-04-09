package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.repository.AdminRepository;
import com.project.Real_Moment.presentation.dto.AdminDto;
import com.project.Real_Moment.presentation.dto.CondDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public boolean checkIdDuplicate(AdminDto.CheckIdDuplicate dto) {
        return adminRepository.existsByLoginId(dto.getLoginId());
    }

    @Transactional(readOnly = true)
    public AdminDto.AdminListWrapper getAdminList(CondDto.AdminListCond dto) {

        Pageable pageable = PageRequest.of(dto.getNowPage() - 1, 9);

        Page<Admin> adminListPaging = adminRepository.findAdminListByPaging(pageable, dto);

        List<AdminDto.AdminList> adminListDto = adminListPaging.stream()
                .map(AdminDto.AdminList::new)
                .toList();

        return new AdminDto.AdminListWrapper(adminListDto, adminListPaging.getTotalPages(), dto.getNowPage());
    }

    @Transactional(readOnly = true)
    public AdminDto.AdminList getAdminDet(Long adminId) {
        Admin admin = adminCheckValidity(adminId);

        return new AdminDto.AdminList(admin);
    }

    @Transactional
    public AdminDto.AdminList editAdminInfo(Long adminId, AdminDto.AdminInfo dto) {
        adminRepository.updateByAdminInfo(adminId, dto);

        Admin admin = adminCheckValidity(adminId);

        return new AdminDto.AdminList(admin);
    }

    @Transactional
    public void editAdminRoles(AdminDto.AdminRolesInfo dto) {
        adminCheckValidity(dto.getAdminId());
        adminRepository.updateRolesByAdminInfo(dto);
    }

    @Transactional
    public void deleteAdmin(Long adminId) {
        adminCheckValidity(adminId);

        adminRepository.deleteAdmin(adminId);
    }

    private Admin adminCheckValidity(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));
    }

    @Transactional
    public void adminJoin(AdminDto.AdminJoin requestDto) {
        adminRepository.save(requestDto.toEntity());
    }
}