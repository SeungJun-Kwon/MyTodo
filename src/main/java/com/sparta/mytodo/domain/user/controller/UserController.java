package com.sparta.mytodo.domain.user.controller;

import com.sparta.mytodo.domain.user.dto.SignUpRequestDto;
import com.sparta.mytodo.domain.user.dto.UserResponseDto;
import com.sparta.mytodo.global.dto.ResponseDto;
import com.sparta.mytodo.domain.user.service.UserServiceImpl;
import com.sparta.mytodo.global.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<?>> signup(
        @Valid @RequestBody SignUpRequestDto signUpRequestDto,
        BindingResult bindingResult) {

        ValidationUtil.validateRequestDto(bindingResult);

        UserResponseDto dto = userServiceImpl.signup(signUpRequestDto);

        return ResponseEntity.ok(ResponseDto.builder()
            .data(dto)
            .build());
    }
}