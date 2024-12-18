package by.polikarpov.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(
            MaxUploadSizeExceededException ex
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Файл слишком большой! Максимальный размер: " + ex.getMaxUploadSize());
    }
}
