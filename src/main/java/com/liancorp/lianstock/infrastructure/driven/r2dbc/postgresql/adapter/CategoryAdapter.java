package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.CategoryEntity;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ICategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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

    @Override
    public Mono<ContentPage<Category>> findAllCategories(int page, int size, String sortBy, boolean isAsc) {
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryRepository.findAllBy(pageable)
                .collectList()
                .zipWith(categoryRepository.count())
                .map(tuple -> {
                    //Page info to Category Entity
                    Page<CategoryEntity> paged = new PageImpl<>(tuple.getT1(), pageable, tuple.getT2());

                    //Mapping to ContentPage<Category>
                    ContentPage<Category> contentPage = new ContentPage<>();
                    contentPage.setTotalPage(paged.getTotalPages());
                    contentPage.setTotalElements(paged.getTotalElements());
                    contentPage.setTotalElements(paged.getTotalElements());
                    contentPage.setPageNumber(page);
                    contentPage.setPageSize(size);
                    contentPage.setFirst(paged.isFirst());
                    contentPage.setLast(paged.isLast());
                    contentPage.setContent(
                            paged.getContent()
                                    .stream()
                                    .map(categoryEntityMapper::toModelFromEntity)
                                    .toList()
                    );
                    return contentPage;
                });
    }

}
