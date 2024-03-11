package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminCategoryService;
import com.project.Real_Moment.application.admin.AdminService;
import com.project.Real_Moment.domain.repository.CategoryRepository;
import com.project.Real_Moment.presentation.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryDto.SaveCategoryResponse dto) {
        adminCategoryService.saveCategory(dto);
        return ResponseEntity.ok().build();
    }
}
