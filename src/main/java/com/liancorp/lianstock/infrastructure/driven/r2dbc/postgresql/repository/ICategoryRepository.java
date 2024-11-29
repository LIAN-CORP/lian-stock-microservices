package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository;

import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.CategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ICategoryRepository extends ReactiveCrudRepository<CategoryEntity, UUID> {
    Mono<Boolean> existsByName(String name);
    Flux<CategoryEntity> findAllBy(Pageable pageable);
}
