package com.giahuy.demo.Service;
import com.giahuy.demo.dto.request.CategoryRequest;
import com.giahuy.demo.dto.response.CategoryResponse;
import com.giahuy.demo.entity.Category;
import com.giahuy.demo.mapper.CategoryMapper;
import com.giahuy.demo.repository.CateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryMapper categoryMapper;
    CateRepository cateRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.toCategory(categoryRequest);

        category = cateRepository.save(category);

        return categoryMapper.toCategoryResponse(category);

    }

    public List<CategoryResponse> getAllCategory() {
        return cateRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse).toList();
    }


}
