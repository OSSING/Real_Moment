package com.project.Real_Moment.application.admin;

import com.project.Real_Moment.domain.entity.Category;
import com.project.Real_Moment.domain.entity.Item;
import com.project.Real_Moment.domain.repository.CategoryRepository;
import com.project.Real_Moment.domain.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    @Transactional
    public void saveCategory(CategoryDto.SaveCategoryResponse dto) {

        Category parentCategory = null;

        // 자식 카테고리를 저장할 때 (부모 카테고리의 ID가 같이 요청됐을 때)
        if (dto.getParentId() != null) {
            parentCategory = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));

            if (parentCategory.getParent() != null) {
                throw new IllegalArgumentException("지정된 부모 카테고리의 id는 자식 카테고리입니다.");
            }
        }

        Category category = dto.toEntity(parentCategory);

        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto.CategoryList> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto.CategoryList::new).toList();
    }

    @Transactional
    public void editCategory(CategoryDto.CategoryList dto) {
        checkUpdateCategoryValidity(dto.getCategoryId(), dto.getParentId());
        categoryRepository.updateCategory(dto);
    }

    private void checkUpdateCategoryValidity(Long categoryId, Long parentId) {
        // 수정될 대상 카테고리 ID가 존재하는지 체크
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        // 자식 카테고리라면 (부모가 있다면)
        if (category.getParent() != null) {
            // 수정될 부모 카테고리가 존재하는지 체크
            if (parentId != null) {
                categoryRepository.findById(parentId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다."));
            } else {
                throw new IllegalArgumentException("자식 카테고리는 부모 카테고리가 될 수 없습니다.");
            }
        // 부모 카테고리라면 (부모가 없다면)
        } else {
            if (parentId != null) {
                throw new IllegalArgumentException("부모 카테고리는 자식 카테고리가 될 수 없습니다.");
            }
        }
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = checkDeleteCategoryValidity(categoryId);
        categoryRepository.delete(category);
    }

    private Category checkDeleteCategoryValidity(Long categoryId) {
        // 수정될 대상 카테고리 ID가 존재하는지 체크
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        List<Item> itemCheck = itemRepository.findByCategoryId(category);

        if (!itemCheck.isEmpty()) {
            throw new IllegalArgumentException("삭제할 카테고리에 속한 상품이 있습니다.");
        } else {
            return category;
        }
    }
}
