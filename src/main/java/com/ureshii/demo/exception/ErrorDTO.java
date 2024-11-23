package com.ureshii.demo.exception;

public record ErrorDTO(int httpStatus, String title, String description) {
}
