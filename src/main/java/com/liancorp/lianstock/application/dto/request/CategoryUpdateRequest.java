package com.liancorp.lianstock.application.dto.request;

import com.liancorp.lianstock.application.constants.RequestConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryUpdateRequest(
        @NotEmpty(message = RequestConstants.CATEGORY_UUID_MUST_NOT_BE_NULL)
        String id,
        @Size(max = RequestConstants.CATEGORY_NAME_MAX_LENGTH, message = RequestConstants.CATEGORY_NAME_IS_ABOVE_THE_LIMIT)
        @Pattern(regexp = RequestConstants.ONLY_LETTERS_REGEX)
        String name,
        @Size(max = RequestConstants.CATEGORY_DESCRIPTION_MAX_LENGTH, message = RequestConstants.CATEGORY_DESCRIPTION_IS_ABOVE_THE_LIMIT)
        @Pattern(regexp = RequestConstants.ONLY_LETTERS_REGEX)
        String description
) {
}
