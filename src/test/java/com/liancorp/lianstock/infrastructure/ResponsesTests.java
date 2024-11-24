package com.liancorp.lianstock.infrastructure;

import com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler.AnswerResponse;
import com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler.DataResponse;
import com.liancorp.lianstock.infrastructure.driving.http.exceptionhandler.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class ResponsesTests {

    @Test
    void shouldInitAnswerResponse() {
        //Arrange
        DataResponse dataResponse = DataResponse.builder().message("message").build();
        var status = HttpStatus.OK;

        //Act
        AnswerResponse answerResponse = new AnswerResponse(status, dataResponse);

        //Assert
        Assertions.assertNotNull(answerResponse.getTimestamp());
        Assertions.assertNotNull(answerResponse.getStatus());
        Assertions.assertNotNull(answerResponse.getDataResponse());
    }

    @Test
    void shouldInitErrorResponse() {
        //Arrange
        var status = HttpStatus.OK;
        int code = HttpStatus.OK.value();

        //Act
        ErrorResponse errorResponse = new ErrorResponse(status, "message", code);

        //Assert
        Assertions.assertNotNull(errorResponse.getTimestamp());
        Assertions.assertNotNull(errorResponse.getStatus());
        Assertions.assertNotNull(errorResponse.getMessage());
    }
}
