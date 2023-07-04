package com.ureshii.demo.exception;

import jakarta.validation.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ContentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST, Objects.requireNonNull(ex.getMessage()).toUpperCase(), ""),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.NOT_FOUND, Objects.requireNonNull(ex.getMessage()).toUpperCase(), ""),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<ErrorDTO> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.CONFLICT, Objects.requireNonNull(ex.getMessage()).toUpperCase(), ""),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserBlockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<ErrorDTO> handleForbiddenException(UserBlockedException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.FORBIDDEN, Objects.requireNonNull(ex.getMessage()).toUpperCase(), ""),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoChangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleForbiddenException(NoChangeException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST, Objects.requireNonNull(ex.getMessage()).toUpperCase(), ""),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorDTO(BAD_REQUEST, ex.getMessage().toUpperCase(), ""),
                BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorDTO> handleInvalidParameterException(InvalidParameterException ex) {

        return new ResponseEntity<>(new ErrorDTO(BAD_REQUEST, ex.getMessage().toUpperCase(), ""),
                BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleMultipartException(MultipartException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(BAD_REQUEST, Objects.requireNonNull(ex.getMessage()).toUpperCase(), ""),
                BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorDTO dto = new ErrorDTO(BAD_REQUEST, ex.getBody().getDetail(), getErrorDescription(ex));
        return new ResponseEntity<>(dto, BAD_REQUEST);
    }

    private String getErrorDescription(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors.toString();
    }
}
