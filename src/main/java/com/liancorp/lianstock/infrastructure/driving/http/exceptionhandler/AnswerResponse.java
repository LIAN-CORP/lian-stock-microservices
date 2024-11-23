package com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AnswerResponse {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private DataResponse dataResponse;

    public AnswerResponse(HttpStatus status, DataResponse dataResponse) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        this.timestamp = LocalDateTime.of(today, now);
        this.status = status;
        this.dataResponse = dataResponse;
    }

}
