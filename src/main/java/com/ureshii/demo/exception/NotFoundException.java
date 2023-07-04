package com.ureshii.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends Exception {
    private final HttpStatus status;

    public NotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }
}
