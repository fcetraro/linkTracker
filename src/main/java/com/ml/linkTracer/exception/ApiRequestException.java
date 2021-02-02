package com.ml.linkTracer.exception;

public class ApiRequestException  extends RuntimeException {
    public ApiRequestException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
