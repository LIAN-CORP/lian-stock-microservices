package com.liancorp.lianstock.domain.spi;

import com.liancorp.lianstock.domain.model.Category;
import reactor.core.publisher.Mono;

public interface ICategoryPersistencePort {
    Mono<Category> saveCategory(Category category);
    Mono<Boolean> categoryExistsByName(String name);
}
