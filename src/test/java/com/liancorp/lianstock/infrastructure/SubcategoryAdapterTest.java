package com.liancorp.lianstock.infrastructure;

import com.liancorp.lianstock.domain.model.Subcategory;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter.SubcategoryAdapter;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.SubcategoryEntity;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ISubcategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ISubcategoryRepository;
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
public class SubcategoryAdapterTest {

    @Mock
    private ISubcategoryEntityMapper subcategoryEntityMapper;
    @Mock
    private ISubcategoryRepository subcategoryRepository;

    private SubcategoryAdapter subcategoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        subcategoryAdapter = new SubcategoryAdapter(subcategoryEntityMapper, subcategoryRepository);
    }

    @Test
    void shouldSaveSubcategoryInDatabase() {
        //Arrange
        Subcategory subcategory = new Subcategory(null, "test", "test", UUID.randomUUID());
        SubcategoryEntity subcategoryEntity = new SubcategoryEntity(UUID.randomUUID(), "test", "test", subcategory.getCategoryId());
        Mockito.when(subcategoryEntityMapper.toEntityFromModel(subcategory)).thenReturn(subcategoryEntity);
        Mockito.when(subcategoryRepository.save(subcategoryEntity)).thenReturn(Mono.just(subcategoryEntity));
        Subcategory resultModel = new Subcategory(subcategoryEntity.getId(), "test", "test", subcategory.getCategoryId());
        Mockito.when(subcategoryEntityMapper.toModelFromEntity(subcategoryEntity)).thenReturn(resultModel);

        //Act
        Mono<Subcategory> result = subcategoryAdapter.saveSubcategory(subcategory);
        //Assert
        StepVerifier.create(result)
                .expectNextMatches(sub -> sub.getCategoryId().equals(subcategory.getCategoryId()))
                .expectComplete()
                .verify();

        verify(subcategoryEntityMapper, times(1)).toEntityFromModel(subcategory);
        verify(subcategoryEntityMapper, times(1)).toModelFromEntity(subcategoryEntity);
        verify(subcategoryRepository, times(1)).save(subcategoryEntity);
    }

}
