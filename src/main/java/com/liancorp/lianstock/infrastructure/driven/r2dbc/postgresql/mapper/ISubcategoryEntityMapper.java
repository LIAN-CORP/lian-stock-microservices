package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper;

import com.liancorp.lianstock.domain.model.Subcategory;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.SubcategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISubcategoryEntityMapper {
    SubcategoryEntity toEntityFromModel(Subcategory subcategory);
    Subcategory toModelFromEntity(SubcategoryEntity subcategoryEntity);
}
