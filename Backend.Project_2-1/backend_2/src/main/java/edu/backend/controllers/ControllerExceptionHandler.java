package edu.backend.controllers;

import edu.backend.util.ErrorResponseDTO;
import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(@Nonnull final Exception e) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder().errorMessage(e.getMessage()).build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
