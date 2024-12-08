package com.liancorp.lianstock.infrastructure;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter.CategoryAdapter;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.CategoryEntity;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ICategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ICategoryEntityMapper categoryEntityMapper;
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryAdapter = new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    void shouldSaveCategorySuccessfully() {
        //Arrange
        Category category = new Category(null, "test", "test");
        CategoryEntity entity = new CategoryEntity(UUID.randomUUID(), "test", "test");
        when(categoryEntityMapper.toEntityFromModel(category)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(Mono.just(entity));
        Category categoryResponse = new Category(entity.getId(), "test", "test");
        when(categoryEntityMapper.toModelFromEntity(entity)).thenReturn(categoryResponse);
        //Act
        Mono<Category> result = categoryAdapter.saveCategory(category);

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(cat -> cat.getId().equals(entity.getId()))
                .verifyComplete();
        verify(categoryEntityMapper, times(1)).toEntityFromModel(category);
        verify(categoryEntityMapper, times(1)).toModelFromEntity(entity);
        verify(categoryRepository, times(1)).save(entity);
    }

    @Test
    void shouldReturnTrueWhenCategoryExistsByName() {
        //Arrange
        String name = "test";
        when(categoryRepository.existsByName(name)).thenReturn(Mono.just(true));
        //Act
        Mono<Boolean> result = categoryAdapter.categoryExistsByName(name);

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(res -> res.equals(true))
                .verifyComplete();

        verify(categoryRepository, times(1)).existsByName(name);
    }

    @Test
    void shouldFindAllCategoriesAscendingSuccessfully() {
        //Arrange
        int page = 0;
        int size = 5;
        String sortBy = "name";
        boolean isAsc = true;
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        CategoryEntity categoryEntity = new CategoryEntity(UUID.randomUUID(), "test name", "test description");
        Category category = new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getDescription());
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        List<Category> categories = List.of(category);
        when(categoryRepository.findAllBy(pageable)).thenReturn(Flux.fromIterable(categoryEntities));
        when(categoryRepository.count()).thenReturn(Mono.just(1L));
        when(categoryEntityMapper.toModelFromEntity(categoryEntity)).thenReturn(category);

        //Act
        Mono<ContentPage<Category>> result = categoryAdapter.findAllCategories(page, size, sortBy, isAsc);

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(contentPage ->
                        contentPage.getTotalPage() == 1 &&
                                contentPage.getTotalElements() == 1 &&
                                contentPage.getPageNumber() == page &&
                                contentPage.getPageSize() == size &&
                                contentPage.isFirst() &&
                                contentPage.isLast() &&
                                contentPage.getContent().equals(categories)
                )
                .verifyComplete();

        verify(categoryRepository, times(1)).findAllBy(pageable);
        verify(categoryRepository, times(1)).count();
        verify(categoryEntityMapper, times(1)).toModelFromEntity(categoryEntity);
    }

    @Test
    void shouldFindAllCategoriesDescendingSuccessfully() {
        //Arrange
        int page = 0;
        int size = 5;
        String sortBy = "name";
        boolean isAsc = false;
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        CategoryEntity categoryEntity = new CategoryEntity(UUID.randomUUID(), "test name", "test description");
        Category category = new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getDescription());
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        List<Category> categories = List.of(category);
        when(categoryRepository.findAllBy(pageable)).thenReturn(Flux.fromIterable(categoryEntities));
        when(categoryRepository.count()).thenReturn(Mono.just(1L));
        when(categoryEntityMapper.toModelFromEntity(categoryEntity)).thenReturn(category);

        //Act
        Mono<ContentPage<Category>> result = categoryAdapter.findAllCategories(page, size, sortBy, isAsc);

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(contentPage ->
                        contentPage.getTotalPage() == 1 &&
                                contentPage.getTotalElements() == 1 &&
                                contentPage.getPageNumber() == page &&
                                contentPage.getPageSize() == size &&
                                contentPage.isFirst() &&
                                contentPage.isLast() &&
                                contentPage.getContent().equals(categories)
                )
                .verifyComplete();

        verify(categoryRepository, times(1)).findAllBy(pageable);
        verify(categoryRepository, times(1)).count();
        verify(categoryEntityMapper, times(1)).toModelFromEntity(categoryEntity);
    }

    @Test
    void shouldReturnAnyBooleanWhenCategoryExistsByIdMethodIsInvoke() {
        //Arrange
        UUID categoryId = UUID.randomUUID();
        when(categoryAdapter.categoryExistsById(categoryId)).thenReturn(Mono.just(true));
        //Act
        Mono<Boolean> result = categoryAdapter.categoryExistsById(categoryId);
        //Assert
        StepVerifier.create(result)
                .expectNextMatches(res -> res.equals(true))
                .verifyComplete();

        verify(categoryRepository, times(1)).existsById(categoryId);
    }

}
