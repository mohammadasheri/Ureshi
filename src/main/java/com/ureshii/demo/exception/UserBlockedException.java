package com.ureshii.demo.exception;

import org.hibernate.service.spi.ServiceException;

public class UserBlockedException extends ServiceException {
    public UserBlockedException(String message) {
        super(message);
    }
}
