package com.liancorp.lianstock.domain;

import com.liancorp.lianstock.domain.api.usecase.CategoryUseCase;
import com.liancorp.lianstock.domain.exception.CategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
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
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryUseCase = new CategoryUseCase(categoryPersistencePort);
    }

    @Test
    void shouldSaveCategoryWhenCategoryNotExist() {
        // Arrange
        Category category = new Category(UUID.randomUUID(), "test", "test desc");
        when(categoryPersistencePort.categoryExistsByName(category.getName())).thenReturn(Mono.just(false));
        when(categoryPersistencePort.saveCategory(category)).thenReturn(Mono.just(category));
        // Act
        Mono<Category> result = categoryUseCase.saveCategory(category);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(savedCategory ->
                        savedCategory.getId().equals(category.getId())
                                && savedCategory.getName().equals(category.getName())
                                && savedCategory.getDescription().equals(category.getDescription())
                )
                .verifyComplete();

        verify(categoryPersistencePort, times(1)).categoryExistsByName(category.getName());
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void shouldThrowErrorWhenCategoryExist() {
        // Arrange
        Category category = new Category(UUID.randomUUID(), "test", "test desc");
        when(categoryPersistencePort.categoryExistsByName(category.getName())).thenReturn(Mono.just(true));
        when(categoryPersistencePort.saveCategory(category)).thenReturn(Mono.just(category));
        // Act
        Mono<Category> result = categoryUseCase.saveCategory(category);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof CategoryAlreadyExistsException)
                .verify();

        verify(categoryPersistencePort, times(1)).categoryExistsByName(category.getName());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }

    @Test
    void shouldReturnAllCategories() {
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
        when(categoryPersistencePort.findAllCategories(page, size, sortBy, isAsc)).thenReturn(Mono.just(expectedContentPage));

        //Act
        Mono<ContentPage<Category>> result = categoryUseCase.findAllCategories(page, size, sortBy, isAsc);

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

        verify(categoryPersistencePort, times(1)).findAllCategories(page, size, sortBy, isAsc);
    }

}
