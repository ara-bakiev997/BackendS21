package edu.backend.utils.error_handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@AllArgsConstructor
public class ErrorResponseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8489159640759667358L;

    private final String message;
    private final HttpStatus httpStatus;
}
