package com.liancorp.lianstock.configuration;

import com.liancorp.lianstock.domain.api.ICategoryServicePort;
import com.liancorp.lianstock.domain.api.usecase.CategoryUseCase;
import com.liancorp.lianstock.domain.spi.ICategoryPersistencePort;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter.CategoryAdapter;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ICategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CategoryBeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

}
