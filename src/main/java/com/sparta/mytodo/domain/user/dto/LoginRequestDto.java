package com.sparta.mytodo.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String email;
    private String password;
}