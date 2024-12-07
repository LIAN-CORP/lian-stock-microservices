package com.liancorp.lianstock.infrastructure.driven.r2dbc.postgresql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("subcategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubcategoryEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
    private UUID categoryId;
}
