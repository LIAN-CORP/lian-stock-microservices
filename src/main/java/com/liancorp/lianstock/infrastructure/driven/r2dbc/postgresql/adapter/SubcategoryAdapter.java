package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter;

import com.liancorp.lianstock.domain.model.Subcategory;
import com.liancorp.lianstock.domain.spi.ISubcategoryPersistencePort;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.SubcategoryEntity;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ISubcategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ISubcategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SubcategoryAdapter implements ISubcategoryPersistencePort {

    private final ISubcategoryEntityMapper subcategoryEntityMapper;
    private final ISubcategoryRepository subcategoryRepository;

    @Override
    public Mono<Subcategory> saveSubcategory(Subcategory subcategory) {
        Mono<SubcategoryEntity> response = subcategoryRepository.save(subcategoryEntityMapper.toEntityFromModel(subcategory));
        return response.map(subcategoryEntityMapper::toModelFromEntity);
    }

    @Override
    public Mono<Boolean> subcategoryExistsByName(String name) {
        return subcategoryRepository.existsByName(name);
    }
}
