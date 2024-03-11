package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.repository.AdminRepository;
import com.project.Real_Moment.presentation.dto.AdminDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public boolean checkIdDuplicate(AdminDto.CheckIdDuplicate dto) {
        return adminRepository.existsByLoginId(dto.getLoginId());
    }
}
