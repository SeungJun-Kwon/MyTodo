package com.sparta.mytodo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCardRequestDto {
    private String cardname;
    private String content;
}
