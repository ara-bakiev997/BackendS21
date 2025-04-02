package edu.backend.utils.error_handler;

import jakarta.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(@Nonnull final Exception e) {
        if (e instanceof final ErrorResponseException exception) {
            return ResponseEntity.status(exception.getHttpStatus())
                                 .body(ErrorResponseDTO.builder().errorMessage(exception.getMessage()).build());
        }

        return ResponseEntity.badRequest().body(
                ErrorResponseDTO.builder().errorMessage(e.toString()).build()
        );
    }
}
