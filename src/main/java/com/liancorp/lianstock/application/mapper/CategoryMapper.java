package com.liancorp.lianstock.application.mapper;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toModelFromRequest(CategoryRequest categoryRequest);
}
