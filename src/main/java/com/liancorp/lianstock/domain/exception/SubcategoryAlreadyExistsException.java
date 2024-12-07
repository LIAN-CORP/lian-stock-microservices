package com.liancorp.lianstock.domain.exception;

import com.liancorp.lianstock.domain.constants.ErrorConstants;

public class SubcategoryAlreadyExistsException extends RuntimeException{
    public SubcategoryAlreadyExistsException(String name) {
        super(ErrorConstants.SUBCATEGORY_ALREADY_EXISTS + name);
    }
}
