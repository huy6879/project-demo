package com.giahuy.demo.mapper;

import com.giahuy.demo.dto.request.CategoryRequest;
import com.giahuy.demo.dto.response.CategoryResponse;
import com.giahuy.demo.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
