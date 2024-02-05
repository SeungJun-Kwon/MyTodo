package com.sparta.mytodo.controller;

import com.sparta.mytodo.dto.LoginRequestDto;
import com.sparta.mytodo.dto.SignUpRequestDto;
import com.sparta.mytodo.dto.UserResponseDto;
import com.sparta.mytodo.entity.ResponseMessage;
import com.sparta.mytodo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage<?>> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        UserResponseDto dto;

        try {
            // Validation 예외처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                List<String> errorMessages = new ArrayList<>();
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
                    errorMessages.add(fieldError.getDefaultMessage());
                }
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

    @PostMapping("/getToken")
    public ResponseEntity<ResponseMessage<?>> getToken(@RequestBody LoginRequestDto requestDto) {
        String accessToken = "";

        try {
            accessToken = userService.getToken(requestDto);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().
                    body(ResponseMessage.builder()
                            .msg(e.getMessage())
                            .httpCode(HttpStatus.BAD_REQUEST.value())
                            .data(null)
                            .build());
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .body(ResponseMessage.builder()
                        .msg("로그인 완료")
                        .httpCode(HttpStatus.OK.value())
                        .data(requestDto.getUsername() + "\n" + accessToken)
                        .build());
    }

    @GetMapping("/login")
    public void getCookie(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String bearerToken) {
        log.info(userService.getCookie(bearerToken));
    }
}