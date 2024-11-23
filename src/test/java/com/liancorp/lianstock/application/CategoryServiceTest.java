package com.liancorp.lianstock.application;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.mapper.CategoryMapper;
import com.liancorp.lianstock.application.services.CategoryService;
import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.model.Category;
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
class CategoryServiceTest {

    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private CategoryMapper categoryMapper;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryServicePort, categoryMapper);
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        //Arrange
        var request = new CategoryRequest("test", "test");
        Mono<CategoryRequest> monoRequest = Mono.just(request);
        var model = new Category(UUID.randomUUID(), "test", "test");
        when(categoryMapper.toModelFromRequest(new CategoryRequest("test", "test"))).thenReturn(model);
        when(categoryServicePort.saveCategory(model)).thenReturn(Mono.just(model));

        //Act
        Mono<Category> result = categoryService.createCategory(monoRequest);

        //Assert
        StepVerifier.create(result)
                .expectNextMatches(category -> category.getId().equals(model.getId()))
                .verifyComplete();
        verify(categoryMapper, times(1)).toModelFromRequest(request);
        verify(categoryServicePort, times(1)).saveCategory(model);
    }

}
