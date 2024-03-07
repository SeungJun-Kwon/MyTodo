package com.sparta.mytodo.domain.user.exception;

public class SignUpUserExistsException extends RuntimeException {

    public SignUpUserExistsException() {

    }

    public SignUpUserExistsException(String message) {
        super(message);
    }
}
