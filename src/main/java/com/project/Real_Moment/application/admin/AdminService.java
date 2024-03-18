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

        int pageNumber = (dto.getNowPage() != null && dto.getNowPage() > 0) ? dto.getNowPage() : 1;

        Pageable pageable = PageRequest.of(pageNumber - 1, 10);

        Page<Admin> adminListPaging = adminRepository.findAdminListByPaging(pageable, dto);

        List<AdminDto.AdminList> adminListDto = adminListPaging.stream()
                .map(AdminDto.AdminList::new)
                .toList();

        return new AdminDto.AdminListWrapper(adminListDto, adminListPaging.getTotalPages(), pageNumber);
    }
}
