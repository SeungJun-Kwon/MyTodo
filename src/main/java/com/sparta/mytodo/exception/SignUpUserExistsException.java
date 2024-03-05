package com.sparta.mytodo.exception;

public class SignUpUserExistsException extends RuntimeException {

    public SignUpUserExistsException() {

    }

    public SignUpUserExistsException(String message) {
        super(message);
    }
}
