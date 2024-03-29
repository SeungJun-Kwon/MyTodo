package com.sparta.mytodo.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto<T> {

    T data;

    public static <T> ResponseDto<T> ofData(T data) {
        return ResponseDto.<T>builder().data(data).build();
    }
}
