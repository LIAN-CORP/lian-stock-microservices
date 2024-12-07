package com.liancorp.lianstock.configuration;

import com.liancorp.lianstock.domain.api.ISubcategoryServicePort;
import com.liancorp.lianstock.domain.api.usecase.SubcategoryUseCase;
import com.liancorp.lianstock.domain.spi.ISubcategoryPersistencePort;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.adapter.SubcategoryAdapter;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.mapper.ISubcategoryEntityMapper;
import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository.ISubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SubcategoryBeanConfiguration {

    private final ISubcategoryEntityMapper subcategoryEntityMapper;
    private final ISubcategoryRepository subcategoryRepository;
    private final CategoryBeanConfiguration categoryBeanConfiguration;

    @Bean
    public ISubcategoryPersistencePort subcategoryPersistencePort() {
        return new SubcategoryAdapter(subcategoryEntityMapper, subcategoryRepository);
    }

    @Bean
    public ISubcategoryServicePort subcategoryServicePort() {
        return new SubcategoryUseCase(subcategoryPersistencePort(), categoryBeanConfiguration.categoryServicePort());
    }


}
