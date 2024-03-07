package com.sparta.mytodo.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionResponseDto {

    String msg;
    int httpCode;
    HttpStatus httpStatus;
}
