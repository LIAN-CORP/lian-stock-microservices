package com.liancorp.lianstock.domain.spi;

import com.liancorp.lianstock.domain.model.Subcategory;
import reactor.core.publisher.Mono;

public interface ISubcategoryPersistencePort {
    Mono<Subcategory> saveCategory(Subcategory subcategory);
    Mono<Boolean> subcategoryExistsByName(String name);
}
