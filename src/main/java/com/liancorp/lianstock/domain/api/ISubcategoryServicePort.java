package com.liancorp.lianstock.domain.api;

import com.liancorp.lianstock.domain.model.Subcategory;
import reactor.core.publisher.Mono;

public interface ISubcategoryServicePort {
    Mono<Subcategory> saveSubcategory(Subcategory subcategory);
}
