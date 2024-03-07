package com.sparta.mytodo.global.util;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Slf4j
public class ValidationUtil {

    public static List<String> getErrors(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            return null;
        }

        // Validation 예외처리
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return errorMessages;
    }

    public static void validateRequestDto(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            return;
        }

        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            errorMessages.add(fieldError.getDefaultMessage());
        }

        throw new IllegalArgumentException(errorMessages.toString());
    }
}
