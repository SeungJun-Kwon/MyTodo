package com.sparta.mytodo.exception;

import com.sparta.mytodo.entity.ResponseMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMessage<Void>> handleException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(ResponseMessage.<Void>builder().httpCode(HttpStatus.BAD_REQUEST.value())
                .msg(ex.getMessage()).build());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ResponseMessage<Void>> handleException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
            .body(ResponseMessage.<Void>builder().httpCode(HttpStatus.NOT_FOUND.value())
                .msg(ex.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage<Void>> handleException(MethodArgumentNotValidException ex) {

        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(ResponseMessage.<Void>builder().httpCode(HttpStatus.BAD_REQUEST.value())
                .msg(errorMessages.toString()).build());
    }
}