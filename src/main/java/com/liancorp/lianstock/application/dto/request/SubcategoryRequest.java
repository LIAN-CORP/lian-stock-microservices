package com.liancorp.lianstock.application.dto.request;

import com.liancorp.lianstock.application.constants.RequestConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SubcategoryRequest(
        @NotNull(message = RequestConstants.SUBCATEGORY_NAME_MUST_NOT_BE_NULL)
        @Size(max = RequestConstants.SUBCATEGORY_NAME_MAX_LENGTH, message = RequestConstants.SUBCATEGORY_NAME_IS_ABOVE_THE_LIMIT)
        @Pattern(regexp = RequestConstants.ONLY_LETTERS_REGEX)
        String name,

        @NotNull(message = RequestConstants.SUBCATEGORY_DESCRIPTION_MUST_NOT_BE_NULL)
        @Size(max = RequestConstants.SUBCATEGORY_DESCRIPTION_MAX_LENGTH, message = RequestConstants.SUBCATEGORY_DESCRIPTION_IS_ABOVE_THE_LIMIT)
        @Pattern(regexp = RequestConstants.ONLY_LETTERS_REGEX)
        String description,

        @NotNull(message = RequestConstants.CATEGORY_UUID_MUST_NOT_BE_NULL)
        String categoryId
) {
}
