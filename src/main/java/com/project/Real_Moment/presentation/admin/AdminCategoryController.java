package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminCategoryService;
import com.project.Real_Moment.presentation.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CategoryDto.CategoryList>> getCategoryList() {
        return ResponseEntity.ok().body(adminCategoryService.getCategoryList());
    }

    @PatchMapping
    public ResponseEntity<Void> editCategory(@RequestBody CategoryDto.CategoryList dto) {
        adminCategoryService.editCategory(dto);
        return ResponseEntity.ok().build();
    }
}
