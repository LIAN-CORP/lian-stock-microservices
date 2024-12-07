package com.liancorp.lianstock.domain.api.usecase;

import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.api.ISubcategoryServicePort;
import com.liancorp.lianstock.domain.constants.ErrorConstants;
import com.liancorp.lianstock.domain.exception.CategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.exception.SubcategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.model.Subcategory;
import com.liancorp.lianstock.domain.spi.ISubcategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SubcategoryUseCase implements ISubcategoryServicePort {

    private final ISubcategoryPersistencePort subcategoryPersistencePort;
    private final ICategoryServicePort categoryServicePort;


    @Override
    public Mono<Subcategory> saveSubcategory(Subcategory subcategory) {
        return subcategoryPersistencePort.subcategoryExistsByName(subcategory.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new SubcategoryAlreadyExistsException(subcategory.getName()));
                    }
                    return categoryServicePort.categoryExistsById(subcategory.getCategoryId())
                            .flatMap(categoryExists -> {
                                if (!categoryExists) {
                                    return Mono.error(new CategoryAlreadyExistsException(ErrorConstants.CATEGORY_NOT_EXISTS_WITH_UUID +subcategory.getCategoryId().toString()));
                                }
                                return subcategoryPersistencePort.saveCategory(subcategory);
                            });
                });
    }
}
