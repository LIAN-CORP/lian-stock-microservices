package com.liancorp.lianstock.domain.api;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import reactor.core.publisher.Mono;

public interface ICategoryServicePort {
    Mono<Category> saveCategory(Category category);
    Mono<ContentPage<Category>> findAllCategories(int page, int size, String sortBy, boolean isAsc);
}
