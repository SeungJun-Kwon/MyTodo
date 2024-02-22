package com.sparta.mytodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sparta.mytodo.dto.SignUpRequestDto;
import com.sparta.mytodo.dto.UserResponseDto;
import com.sparta.mytodo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입")
    void signUp() {
        // given
        String username = "abc123";
        String password = "abc12345";

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().username(username)
            .password(password).build();

        UserService userService = new UserService(userRepository, passwordEncoder);

        // when
        UserResponseDto userResponseDto = userService.signup(signUpRequestDto);

        // then
        assertNotNull(userResponseDto);
        assertEquals(username, userResponseDto.getUsername());
    }
}
