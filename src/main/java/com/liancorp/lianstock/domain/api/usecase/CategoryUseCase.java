package com.liancorp.lianstock.domain.api.usecase;

import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.constants.ErrorConstants;
import com.liancorp.lianstock.domain.exception.CategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
}
