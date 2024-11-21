package com.liancorp.lianstock.domain.api.usecase;

import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    @Override
    public Mono<Void> saveCategory(Category category) {
        return categoryPersistencePort.saveCategory(category);
    }
}
