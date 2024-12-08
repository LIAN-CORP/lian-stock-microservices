package com.liancorp.lianstock.domain;

import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.api.usecase.SubcategoryUseCase;
import com.liancorp.lianstock.domain.exception.CategoryIdDoNotExists;
import com.liancorp.lianstock.domain.exception.SubcategoryAlreadyExistsException;
import com.liancorp.lianstock.domain.model.Subcategory;
import com.liancorp.lianstock.domain.spi.ISubcategoryPersistencePort;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
class SubcategoryUseCaseTest {

    @Mock
    private ISubcategoryPersistencePort subcategoryPersistencePort;
    @Mock
    private ICategoryServicePort categoryServicePort;

    private SubcategoryUseCase subcategoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subcategoryUseCase = new SubcategoryUseCase(subcategoryPersistencePort, categoryServicePort);
    }

    @Test
    void shouldThrowErrorWhenSubcategoryNameExists() {
        //Arrange
        String subcategoryName = "Test";
        Subcategory subcategory = new Subcategory(null, subcategoryName, "test", UUID.randomUUID());
        Mockito.when(subcategoryPersistencePort.subcategoryExistsByName(subcategoryName)).thenReturn(Mono.just(true));

        //Act & Assert
        StepVerifier.create(subcategoryUseCase.saveSubcategory(subcategory))
                .expectErrorMatches(throwable -> throwable instanceof SubcategoryAlreadyExistsException)
                .verify();

        verify(subcategoryPersistencePort, times(1)).subcategoryExistsByName(subcategoryName);
        verifyNoInteractions(categoryServicePort);
        verify(subcategoryPersistencePort, never()).saveSubcategory(any());
    }

    @Test
    void shouldThrowErrorWhenCategoryAssociateDoNotExists() {
        //Arrange
        Subcategory subcategory = new Subcategory(null, "test", "test", UUID.randomUUID());
        Mockito.when(subcategoryPersistencePort.subcategoryExistsByName(subcategory.getName())).thenReturn(Mono.just(false));
        Mockito.when(categoryServicePort.categoryExistsById(subcategory.getCategoryId())).thenReturn(Mono.just(false));
        //Act && Assert
        StepVerifier.create(subcategoryUseCase.saveSubcategory(subcategory))
                .expectErrorMatches(throwable -> throwable instanceof CategoryIdDoNotExists)
                .verify();

        verify(subcategoryPersistencePort, times(1)).subcategoryExistsByName(subcategory.getName());
        verify(categoryServicePort, times(1)).categoryExistsById(subcategory.getCategoryId());
        verify(subcategoryPersistencePort, never()).saveSubcategory(any());
    }

    @Test
    void shouldSaveSubcategorySuccessfully() {
        //Arrange
        Subcategory subcategory = new Subcategory(null, "test", "test", UUID.randomUUID());
        when(subcategoryPersistencePort.subcategoryExistsByName(subcategory.getName())).thenReturn(Mono.just(false));
        when(categoryServicePort.categoryExistsById(subcategory.getCategoryId())).thenReturn(Mono.just(true));
        when(subcategoryPersistencePort.saveSubcategory(subcategory)).thenReturn(Mono.just(subcategory));

        //Act
        Mono<Subcategory> result = subcategoryUseCase.saveSubcategory(subcategory);
        //Assert
        StepVerifier.create(result)
                .expectNext(subcategory)
                .verifyComplete();

        verify(subcategoryPersistencePort, times(1)).subcategoryExistsByName(subcategory.getName());
        verify(categoryServicePort, times(1)).categoryExistsById(subcategory.getCategoryId());
        verify(subcategoryPersistencePort, times(1)).saveSubcategory(subcategory);
    }

}
