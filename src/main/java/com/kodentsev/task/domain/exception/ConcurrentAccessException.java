package com.kodentsev.task.domain.exception;

public class ConcurrentAccessException extends RuntimeException{

    public ConcurrentAccessException(String message) {
        super(message);
    }
}
