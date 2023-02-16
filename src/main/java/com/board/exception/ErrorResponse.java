package com.board.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter
@Setter
public class ErrorResponse {

    // General error message about nature of error
    private String message;

    // Specific errors in API request processing
    private List<String> details;

    private String timestamp;

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
