package com.ureshii.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAllowedException extends Exception {
    private final HttpStatus status;

    public NotAllowedException(String message) {
        super(message);
        status = HttpStatus.METHOD_NOT_ALLOWED;
    }

    public NotAllowedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
