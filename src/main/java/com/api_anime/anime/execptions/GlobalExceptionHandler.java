package com.api_anime.anime.execptions;

import com.api_anime.anime.model.ObjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // Customize the exception handling logic here
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ObjectResponse(400, "An error occurred: " + ex.getMessage()));
    }

    // You can add more @ExceptionHandler methods for specific exceptions as needed.
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleCustomException(BadRequestException ex) {
        // Customize the handling for CustomException
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ObjectResponse(400, "Custom exception occurred: " + ex.getMessage()));
    }
}
