package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.CategoryEntity;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ICategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public Mono<Category> saveCategory(Category category) {
        Mono<CategoryEntity> responseEntity = categoryRepository.save(categoryEntityMapper.toEntityFromModel(category));
        return responseEntity.map(categoryEntityMapper::toModelFromEntity);
    }

    @Override
    public Mono<Boolean> categoryExistsByName(String name) {
        return categoryRepository.existsByName(name);
    }

}
