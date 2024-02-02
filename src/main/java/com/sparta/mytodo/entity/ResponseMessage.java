package com.sparta.mytodo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseMessage<T> {
    int httpCode;
    String msg;
    T data;
}
