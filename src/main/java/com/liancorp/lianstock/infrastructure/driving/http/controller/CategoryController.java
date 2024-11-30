package com.liancorp.lianstock.infrastructure.driving.http.controller;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.dto.request.CategoryUpdateRequest;
import com.liancorp.lianstock.application.services.CategoryService;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Mono<Category> createCategory(@Valid @RequestBody Mono<CategoryRequest> request) {
        return this.categoryService.createCategory(request);
    }

    @GetMapping
    public Mono<ResponseEntity<Mono<ContentPage<Category>>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc
    ) {
        return Mono.just(
                ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(categoryService.findAllCategories(page, size, sortBy, isAsc))
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateCategory(@Valid @RequestBody Mono<CategoryUpdateRequest> request) {
        return request.flatMap(req -> categoryService.updateCategory(Mono.just(req)))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
