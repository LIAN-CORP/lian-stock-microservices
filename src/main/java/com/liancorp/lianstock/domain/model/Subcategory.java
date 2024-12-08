package com.liancorp.lianstock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subcategory {
    private UUID id;
    private String name;
    private String description;
    private UUID categoryId;
}
