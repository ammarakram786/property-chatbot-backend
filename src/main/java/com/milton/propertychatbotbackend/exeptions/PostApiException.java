package com.milton.propertychatbotbackend.exeptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PostApiException extends RuntimeException {
    @Getter
    private HttpStatus status;
    private final String message;
    public PostApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}