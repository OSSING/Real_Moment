package com.project.Real_Moment.presentation.member;

import com.project.Real_Moment.application.member.CategoryService;
import com.project.Real_Moment.presentation.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto.CategoryListRes>> findAllCategories() {
        return ResponseEntity.ok().body(categoryService.findAllCategory());
    }
}
