package com.liancorp.lianstock.domain.api;

import com.liancorp.lianstock.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICategoryServicePort {
    Mono<Void> saveCategory(Category category);
}
