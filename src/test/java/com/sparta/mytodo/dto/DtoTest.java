package com.sparta.mytodo.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class DtoTest {

    public static Validator validator;

    public List<String> messages;

    @BeforeEach
    void setUp() {
        messages = new ArrayList<>();
    }

    @BeforeAll
    static void beforeAll() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public <T> void validateAndCollectMessages(T requestDto) {
        Set<ConstraintViolation<T>> violations = validator.validate(requestDto);
        for (ConstraintViolation<T> violation : violations) {
            System.out.println(violation.getMessage());
            messages.add(violation.getMessage());
        }
    }
}
