package com.liancorp.lianstock.application.mapper;

import com.liancorp.lianstock.application.dto.request.SubcategoryRequest;
import com.liancorp.lianstock.domain.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface ISubcategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryId", expression = "java(UUID.fromString(request.categoryId()))")
    Subcategory toModelFromRequest(SubcategoryRequest request);
}
