package com.liancorp.lianstock.application;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.mapper.CategoryMapper;
import com.liancorp.lianstock.application.services.CategoryService;
import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
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

    @Test
    void shouldFindAllCategoriesSuccessfully() {
        //Arrange
        int page = 0;
        int size = 5;
        String sortBy = "name";
        boolean isAsc = true;
        Category category = new Category(UUID.randomUUID(), "test", "test desc");
        List<Category> categories = List.of(category);
        ContentPage<Category> expectedContentPage = new ContentPage<>(
                1,
                1L,
                page,
                size,
                true,
                true,
                categories
        );

        when(categoryServicePort.findAllCategories(page, size, sortBy, isAsc)).thenReturn(Mono.just(expectedContentPage));

        //Act
        Mono<ContentPage<Category>> result = categoryService.findAllCategories(page, size, sortBy, isAsc);

        //Assert

        StepVerifier.create(result)
                .expectNextMatches(res ->
                        res.getTotalPage().equals(expectedContentPage.getTotalPage())
                                && res.getTotalElements().equals(expectedContentPage.getTotalElements())
                                && res.getPageNumber().equals(expectedContentPage.getPageNumber())
                                && res.getPageSize().equals(expectedContentPage.getPageSize())
                                && res.isFirst() == expectedContentPage.isFirst()
                                && res.isLast() == expectedContentPage.isLast()
                                && res.getContent().equals(categories)
                )
                .verifyComplete();

        verify(categoryServicePort, times(1)).findAllCategories(page, size, sortBy, isAsc);
    }

}
