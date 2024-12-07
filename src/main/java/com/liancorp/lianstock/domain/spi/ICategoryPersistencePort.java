package com.liancorp.lianstock.domain.spi;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICategoryPersistencePort {
    Mono<Category> saveCategory(Category category);
    Mono<Boolean> categoryExistsByName(String name);
    Mono<ContentPage<Category>> findAllCategories(int page, int size, String sort, boolean isAsc);
    Mono<Boolean> categoryExistsById(UUID id);
}
