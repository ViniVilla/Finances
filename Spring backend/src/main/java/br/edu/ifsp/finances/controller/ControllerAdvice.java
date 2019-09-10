package br.edu.ifsp.finances.controller;

import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ControllerAdvice {

    private ResponseEntity<?> handler(final Exception e, final HttpStatus status){
        var message = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());

        return new ResponseEntity<String>(message, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(final ResourceNotFoundException e){
        return handler(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(final BadRequestException e){
        return handler(e, HttpStatus.BAD_REQUEST);
    }

}
