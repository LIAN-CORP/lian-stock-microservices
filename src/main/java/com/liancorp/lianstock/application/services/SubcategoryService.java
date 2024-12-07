package com.liancorp.lianstock.application.services;

import com.liancorp.lianstock.application.dto.request.SubcategoryRequest;
import com.liancorp.lianstock.application.mapper.ISubcategoryMapper;
import com.liancorp.lianstock.domain.api.ISubcategoryServicePort;
import com.liancorp.lianstock.domain.model.Subcategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubcategoryService {

    private final ISubcategoryServicePort subcategoryServicePort;
    private final ISubcategoryMapper subcategoryMapper;

    public Mono<Subcategory> createSubcategory(Mono<SubcategoryRequest> subcategoryRequest) {
        return subcategoryRequest.map(subcategoryMapper::toModelFromRequest)
                .flatMap(subcategoryServicePort::saveSubcategory);
    }

}
