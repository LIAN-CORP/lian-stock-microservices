package com.liancorp.lianstock.infrastructure.driving.http.controller;

import com.liancorp.lianstock.application.dto.request.SubcategoryRequest;
import com.liancorp.lianstock.application.services.SubcategoryService;
import com.liancorp.lianstock.domain.model.Subcategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @PostMapping
    public Mono<Subcategory> createSubcategory(@Valid @RequestBody Mono<SubcategoryRequest> request) {
        return subcategoryService.createSubcategory(request);
    }

}
