package com.liancorp.lianstock.infrastructure;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.dto.request.CategoryUpdateRequest;
import com.liancorp.lianstock.application.services.CategoryService;
import com.liancorp.lianstock.domain.model.Category;
import com.liancorp.lianstock.domain.model.ContentPage;
import com.liancorp.lianstock.infrastructure.driving.http.controller.CategoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureWebTestClient
@SpringBootTest
class CategoryControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CategoryService categoryService;
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory() {
        //Arrange
        CategoryRequest request = new CategoryRequest("test", "test");
        Category expectedCategory = new Category(UUID.randomUUID(), "test", "test");
        when(categoryService.createCategory(any(Mono.class))).thenReturn(Mono.just(expectedCategory));

        //Act & Assert
        webClient.post()
                .uri("/category")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .value(cat -> {
                    assertEquals(expectedCategory.getId(), cat.getId());
                    assertEquals(expectedCategory.getName(), cat.getName());
                    assertEquals(expectedCategory.getDescription(), cat.getDescription());
                });
    }

    @Test
    void getAllCategories() {
        //Arrange
        int page = 0;
        int size = 5;
        String sortBy = "name";
        boolean isAsc = false;
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
        when(categoryService.findAllCategories(page, size, sortBy, isAsc)).thenReturn(Mono.just(expectedContentPage));

        //Act & Assert
        webClient.get()
                .uri("/category")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ContentPage.class)
                .hasSize(1);
    }

    @Test
    void updateCategory() {
        //Arrange
        CategoryUpdateRequest request = new CategoryUpdateRequest(UUID.randomUUID().toString(),"test", "test");
        //Category expectedCategory = new Category(UUID.fromString(request.id()), "test", "test");
        when(categoryService.updateCategory(any(Mono.class))).thenReturn(Mono.empty());

        //Act & Assert
        webClient.put()
                .uri("/category")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNoContent();

    }

}
