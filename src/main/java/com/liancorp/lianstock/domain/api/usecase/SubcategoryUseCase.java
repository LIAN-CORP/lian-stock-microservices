package com.liancorp.lianstock.domain.api.usecase;

import com.liancorp.lianstock.domain.api.ISubcategoryServicePort;
import com.liancorp.lianstock.domain.exception.SubcategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.model.Subcategory;
import com.liancorp.lianstock.domain.spi.ISubcategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SubcategoryUseCase implements ISubcategoryServicePort {

    private final ISubcategoryPersistencePort subcategoryPersistencePort;


    @Override
    public Mono<Subcategory> saveSubcategory(Subcategory subcategory) {
        return subcategoryPersistencePort.subcategoryExistsByName(subcategory.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new SubcategoryAlreadyExistsException(subcategory.getName()));
                    }
                    return subcategoryPersistencePort.saveCategory(subcategory);
                });
    }
}
