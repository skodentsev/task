package com.kodentsev.task.controller;

import com.kodentsev.task.domain.exception.BadRequestException;
import com.kodentsev.task.domain.exception.ConcurrentAccessException;
import com.kodentsev.task.domain.exception.NoContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionMapper {

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<String> toResponse(NoContentException exception) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> toResponse(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ConcurrentAccessException.class)
    public ResponseEntity<String> toResponse(ConcurrentAccessException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
