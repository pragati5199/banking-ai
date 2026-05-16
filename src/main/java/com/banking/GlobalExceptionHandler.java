package com.banking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handlesResponseStatusException(ResponseStatusException ex){

        ErrorResponse er = new ErrorResponse(
                ex.getStatusCode().value(),
                ex.getReason(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(ex.getStatusCode().value()).body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlesMethodArgumentNotValidException(MethodArgumentNotValidException ex){

       String message = ex.getBindingResult().getFieldErrors()
               .stream()
               .map(error -> error.getField() + ":" + error.getDefaultMessage())
               .collect(Collectors.joining(","));

       ErrorResponse er = new ErrorResponse(400,message,LocalDateTime.now());

        return ResponseEntity.status(ex.getStatusCode().value()).body(er);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlesException(Exception ex){

        ErrorResponse error = new ErrorResponse(
                500,
                "Internal server error",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
