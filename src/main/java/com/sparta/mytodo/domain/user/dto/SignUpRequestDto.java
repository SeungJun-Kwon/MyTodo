package com.sparta.mytodo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = {"password", "isAdmin", "adminToken"})
public class SignUpRequestDto {

    @NotBlank(message = "사용자 이메일이 공백이면 안됩니다.")
    @Size(min = 5, max = 50, message = "사용자 이메일의 크기가 20에서 50 사이여야 합니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "사용자 이름이 공백이면 안됩니다.")
    @Size(min = 2, max = 20, message = "사용자 이름의 크기가 4에서 10 사이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣]*$", message = "사용자 이름은 영어랑 숫자, 한글만 가능합니다.")
    private String userName;

    @NotBlank(message = "사용자 비밀번호가 공백이면 안됩니다.")
    @Size(min = 4, max = 30, message = "사용자 비밀번호의 크기가 8에서 15 사이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "사용자 비밀번호는 영어랑 숫자만 가능합니다.")
    private String password;

    private boolean isAdmin = false;

    private String adminToken = "";
}
