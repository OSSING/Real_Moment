package com.project.Real_Moment.application.member;

import com.project.Real_Moment.domain.entity.Category;
import com.project.Real_Moment.domain.repository.CategoryRepository;
import com.project.Real_Moment.presentation.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto.CategoryListRes> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryDto.CategoryListRes::new)
                .collect(Collectors.toList());
    }
}
