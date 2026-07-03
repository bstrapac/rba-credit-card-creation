package rba.card_creation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardApiException.class)
    public ResponseEntity<ErrorResponse> handleCardApiException(CardApiException ex) {
        ErrorResponse error = new ErrorResponse(
                String.valueOf(ex.getStatusCode()),
                UUID.randomUUID().toString(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleCardReqDeletionException(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                UUID.randomUUID().toString(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(HttpStatus.BAD_REQUEST.toString()));
    }
}
