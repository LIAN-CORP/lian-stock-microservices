package com.liancorp.lianstock.application.mapper;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toModelFromRequest(CategoryRequest request) {
        var category = new Category();
        category.setId(null);
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }
}
