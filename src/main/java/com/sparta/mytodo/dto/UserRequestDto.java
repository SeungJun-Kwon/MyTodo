package com.sparta.mytodo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "사용자 이름이 공백이면 안됩니다.")
    @Size(min = 4, max = 10, message = "사용자 이름의 크기가 4에서 10 사이여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "사용자 이름은 영어(소문자)랑 숫자만 가능합니다.")
    String username;

    @NotBlank(message = "사용자 비밀번호가 공백이면 안됩니다.")
    @Size(min = 8, max = 15, message = "사용자 비밀번호의 크기가 8에서 15 사이여야 합니다.")
    @Pattern (regexp = "^[a-zA-Z0-9]*$", message = "사용자 비밀번호는 영어랑 숫자만 가능합니다.")
    String password;
}
