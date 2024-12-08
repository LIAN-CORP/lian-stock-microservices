package com.liancorp.lianstock.infrastructure;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.dto.request.SubcategoryRequest;
import com.liancorp.lianstock.application.services.CategoryService;
import com.liancorp.lianstock.application.services.SubcategoryService;
import com.liancorp.lianstock.domain.exception.CategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.exception.CategoryIdDoNotExists;
import com.liancorp.lianstock.domain.exception.SubcategoryAlreadyExistsException;
import com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AutoConfigureWebTestClient
@SpringBootTest
class CategoryControllerAdvisorTest {

    @Autowired
    private WebTestClient webClient;

    @Mock
    private CategoryService categoryService;
    @Mock
    private SubcategoryService subcategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldHandleCategoryAlreadyExistException() {
        // Arrange
        CategoryRequest invalidRequest = new CategoryRequest("test", "test");
        Mockito.when(categoryService.createCategory(Mono.just(invalidRequest))).thenThrow(CategoryAlreadyExistsException.class);

        //Act & Assert
        webClient.post()
                .uri("/category")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class);
    }

    @Test
    void shouldHandleSubcategoryAlreadyExistException() {
        //Arrange
        SubcategoryRequest invalidRequest = new SubcategoryRequest("test", "test", UUID.randomUUID().toString());
        Mockito.when(subcategoryService.createSubcategory(Mono.just(invalidRequest))).thenThrow(SubcategoryAlreadyExistsException.class);

        //Act & Assert
        webClient.post()
                .uri("/subcategory")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class);
    }

    @Test
    void shouldHandleCategoryIdDoNotExistException() {
        //Arrange
        SubcategoryRequest invalidRequest = new SubcategoryRequest("test", "test", UUID.randomUUID().toString());
        Mockito.when(subcategoryService.createSubcategory(Mono.just(invalidRequest))).thenThrow(CategoryIdDoNotExists.class);

        //Act & Assert
        webClient.post()
                .uri("/subcategory")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class);
    }

}
