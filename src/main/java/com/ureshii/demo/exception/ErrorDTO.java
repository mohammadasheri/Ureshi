package com.ureshii.demo.exception;

import org.springframework.http.HttpStatus;

public record ErrorDTO(HttpStatus httpStatus, String title, String description) {
}
