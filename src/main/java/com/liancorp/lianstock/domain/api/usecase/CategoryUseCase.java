package com.liancorp.lianstock.domain.api.usecase;

import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.constants.ErrorConstants;
import com.liancorp.lianstock.domain.exception.CategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    @Override
    public Mono<Category> saveCategory(Category category) {
        return categoryPersistencePort.categoryExistsByName(category.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new CategoryAlreadyExistsException(ErrorConstants.CATEGORY_ALREADY_EXISTS + category.getName()));
                    }
                    return categoryPersistencePort.saveCategory(category);
                });
    }

    @Override
    public Mono<ContentPage<Category>> findAllCategories(int page, int size, String sortBy, boolean isAsc) {
        return categoryPersistencePort.findAllCategories(page, size, sortBy, isAsc);
    }

    @Override
    public Mono<Boolean> categoryExistsById(UUID id) {
        return categoryPersistencePort.categoryExistsById(id);
    }
}
