package com.example.tracker.issue;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler (NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException ex)
    {
        Map<String, String> error = Map.of(
            "error", ex.getMessage(),
            "timestamp", LocalDateTime.now().toString(),
            "status", "404"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler (IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex)
    {
        Map<String, String> error = Map.of(
            "error", ex.getMessage(),
            "timestamp", LocalDateTime.now().toString(),
            "status", "400"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
