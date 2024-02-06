package com.sparta.mytodo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ValidationUtil {
    public static List<String> getErrors(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors())
            return null;

        // Validation 예외처리
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return errorMessages;
    }
}
