package com.liancorp.lianstock.domain.exception;

public class CategoryIdDoNotExists extends RuntimeException {
    public CategoryIdDoNotExists(String message) {
        super(message);
    }
}
