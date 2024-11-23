package com.ureshii.demo.exception;

import jakarta.validation.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ContentExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorDTO> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContentLargeException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    ResponseEntity<ErrorDTO> handlePayloadTooLargeException(ContentLargeException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.PAYLOAD_TOO_LARGE.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.NOT_FOUND.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<ErrorDTO> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.CONFLICT.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<ErrorDTO> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.FORBIDDEN.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(),
                        ex.toString()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoChangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleForbiddenException(NoChangeException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(), ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage().toUpperCase(), ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorDTO> handleInvalidParameterException(InvalidParameterException ex) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage().toUpperCase(), ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> handleMultipartException(MultipartException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(ex.getMessage()).toUpperCase(), ex.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getBody().getDetail(), getErrorDescription(ex));
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    private String getErrorDescription(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors.toString();
    }
}
