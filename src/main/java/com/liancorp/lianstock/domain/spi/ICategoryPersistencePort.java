package com.liancorp.lianstock.domain.spi;

import com.liancorp.lianstock.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICategoryPersistencePort {
    Mono<Void> saveCategory(Category category);
}
