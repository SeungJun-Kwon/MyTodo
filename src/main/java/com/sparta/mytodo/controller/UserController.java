package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.LoginRequestDto;
import com.sparta.mytodo.dto.SignUpRequestDto;
import com.sparta.mytodo.dto.UserResponseDto;
import com.sparta.mytodo.entity.ResponseMessage;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.UserService;
import com.sparta.mytodo.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage<?>> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        UserResponseDto dto;

        try {
            // Validation 예외처리
            List<String> errorMessages = ValidationUtil.getErrors(bindingResult);
            if(errorMessages != null && !errorMessages.isEmpty()) {
                return ResponseEntity.badRequest().
                        body(ResponseMessage.builder()
                                .msg(errorMessages.toString())
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .data(null)
                                .build());
            }

            dto = userService.signup(signUpRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().
                    body(ResponseMessage.builder()
                            .msg("회원가입 실패")
                            .httpCode(HttpStatus.BAD_REQUEST.value())
                            .data(null)
                            .build());
        }

        return ResponseEntity.ok(ResponseMessage.builder()
                .msg(signUpRequestDto.getUsername() + " 유저 회원 가입 완료")
                .httpCode(HttpStatus.OK.value())
                .data(dto)
                .build());
    }
}