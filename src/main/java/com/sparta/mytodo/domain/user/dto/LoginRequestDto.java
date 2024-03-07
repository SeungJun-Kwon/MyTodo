package com.sparta.mytodo.domain.user.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
public class LoginRequestDto {

    private String email;
    private String password;
}