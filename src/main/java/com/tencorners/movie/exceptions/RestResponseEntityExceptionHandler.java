package com.tencorners.movie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handlerNotFoundException(Exception exception, WebRequest request) {

        RestErrorResponse errors = new RestErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<RestErrorResponse> customHandleNotFound(Exception exception, WebRequest request) {

        RestErrorResponse errors = new RestErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setPath(((ServletWebRequest)request).getRequest().getRequestURI());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }

}
