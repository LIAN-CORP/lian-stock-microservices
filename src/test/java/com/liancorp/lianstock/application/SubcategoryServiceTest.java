package com.liancorp.lianstock.application;

import com.liancorp.lianstock.application.dto.request.SubcategoryRequest;
import com.liancorp.lianstock.application.mapper.ISubcategoryMapper;
import com.liancorp.lianstock.application.services.SubcategoryService;
import com.liancorp.lianstock.domain.api.ISubcategoryServicePort;
import com.liancorp.lianstock.domain.model.Subcategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SubcategoryServiceTest {
    @Mock
    private ISubcategoryServicePort subcategoryServicePort;
    @Mock
    private ISubcategoryMapper subcategoryMapper;
    private SubcategoryService subcategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subcategoryService = new SubcategoryService(subcategoryServicePort, subcategoryMapper);
    }

    @Test
    void shouldCreateSubcategorySuccessfully() {
        //Arrange
        SubcategoryRequest request = new SubcategoryRequest("test", "test", UUID.randomUUID().toString());
        Mono<SubcategoryRequest> monoRequest = Mono.just(request);
        var modelRequest = new Subcategory(null, request.name(), request.description(), UUID.fromString(request.categoryId()));
        Mockito.when(subcategoryMapper.toModelFromRequest(request)).thenReturn(modelRequest);
        Mockito.when(subcategoryServicePort.saveSubcategory(modelRequest)).thenReturn(Mono.just(modelRequest));
        //Act
        Mono<Subcategory> result = subcategoryService.createSubcategory(monoRequest);
        //Assert
        StepVerifier.create(result)
                .expectNext(modelRequest)
                .verifyComplete();

        verify(subcategoryMapper, times(1)).toModelFromRequest(request);
        verify(subcategoryServicePort, times(1)).saveSubcategory(modelRequest);
    }
}
