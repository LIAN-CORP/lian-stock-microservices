package com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class DataResponse {
    private int statusCode;
    private String message;
    private Optional<Object> data;
}
