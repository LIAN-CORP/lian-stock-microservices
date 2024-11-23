package com.liancorp.lianstock.infrastructure;

import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter.CategoryAdapter;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.CategoryEntity;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ICategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        Mono<Boolean> resutl = categoryAdapter.categoryExistsByName(name);

        //Assert
        StepVerifier.create(resutl)
                .expectNextMatches(res -> res.equals(Boolean.TRUE))
                .verifyComplete();

        verify(categoryRepository, times(1)).existsByName(name);
    }

}
