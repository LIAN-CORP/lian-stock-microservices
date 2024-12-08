package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.repository;

import com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity.SubcategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ISubcategoryRepository extends ReactiveCrudRepository<SubcategoryEntity, UUID> {
    Mono<Boolean> existsByName(String name);
}
