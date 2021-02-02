package com.ml.linkTracer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(e.getMessage(), e, badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(exception, badRequest);
    }
    @ExceptionHandler(value = {InvalidURLException.class})
    public ResponseEntity<Object> handleInvalidURLExceptionException(InvalidURLException e){
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(e.getMessage(), e, badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(exception, badRequest);
    }
}
