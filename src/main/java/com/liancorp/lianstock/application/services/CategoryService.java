package com.liancorp.lianstock.application.services;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.mapper.CategoryMapper;
import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryMapper categoryMapper;

    public Mono<Category> createCategory(Mono<CategoryRequest> categoryRequest) {
        return categoryRequest.map(categoryMapper::toModelFromRequest)
                .flatMap(categoryServicePort::saveCategory);
    }

}
