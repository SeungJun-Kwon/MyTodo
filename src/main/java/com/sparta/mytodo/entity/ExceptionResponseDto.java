package com.sparta.mytodo.entity;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ExceptionResponseDto {

    String msg;
    int httpCode;
    HttpStatus httpStatus;
}
