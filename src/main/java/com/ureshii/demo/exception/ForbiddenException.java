package com.ureshii.demo.exception;

import org.hibernate.service.spi.ServiceException;

public class ForbiddenException extends ServiceException {
    public ForbiddenException(String message) {
        super(message);
    }
}
