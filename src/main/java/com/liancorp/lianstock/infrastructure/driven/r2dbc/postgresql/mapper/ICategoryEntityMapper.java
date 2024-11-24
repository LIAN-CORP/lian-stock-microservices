package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {
    CategoryEntity toEntityFromModel(Category category);
    Category toModelFromEntity(CategoryEntity entity);
}
