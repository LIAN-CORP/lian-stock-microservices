package com.liancorp.lianstock.application.constants;

public class RequestConstants {
    private RequestConstants() {}

    public static final Integer CATEGORY_NAME_MAX_LENGTH = 50;
    public static final Integer CATEGORY_DESCRIPTION_MAX_LENGTH = 150;
    public static final String ONLY_LETTERS_REGEX = "^[a-zA-Z]*$";
    public static final String CATEGORY_NAME_MUST_NOT_BE_NULL = "Category name must not be null";
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_NULL = "Category description must not be null";
    public static final String CATEGORY_NAME_IS_ABOVE_THE_LIMIT = "Category name is above the limit";
    public static final String CATEGORY_DESCRIPTION_IS_ABOVE_THE_LIMIT = "Category description is above the limit";
}
