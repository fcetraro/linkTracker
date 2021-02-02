package com.ml.linkTracer.exception;

public class InvalidURLException extends RuntimeException {
    public InvalidURLException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
