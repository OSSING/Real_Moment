package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Category;
import com.project.Real_Moment.domain.repository.CategoryRepository;
import com.project.Real_Moment.presentation.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveCategory(CategoryDto.SaveCategoryResponse dto) {

        Category parentCategory = null;

        // 자식 카테고리를 저장할 때 (부모 카테고리의 ID가 같이 요청됐을 때)
        if (dto.getParentId() != null) {
            parentCategory = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));
        }

        Category category = dto.toEntity(parentCategory);

        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto.CategoryList> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto.CategoryList::new).toList();
    }
}
