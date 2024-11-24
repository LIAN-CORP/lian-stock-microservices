package com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private int statusCode;
    private String message;

    public ErrorResponse(HttpStatus status, String message, int statusCode) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        this.timestamp = LocalDateTime.of(today, now);
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }

}
