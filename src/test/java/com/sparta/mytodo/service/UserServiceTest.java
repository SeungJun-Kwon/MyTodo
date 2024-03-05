package com.sparta.mytodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sparta.mytodo.dto.SignUpRequestDto;
import com.sparta.mytodo.dto.UserResponseDto;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import com.sparta.mytodo.exception.SignUpUserExistsException;
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

    @Test
    @DisplayName("회원 가입 실패(유저 이미 존재)")
    void signUpUserExists() {
        // given
        String username = "abc123";
        String password = "abc12345";

        User user = new User();
        user.setId(100L);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRoleEnum.USER);
        userRepository.save(user);

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().username(username)
            .password(password).build();

        UserService userService = new UserService(userRepository, passwordEncoder);

        // when - then
        try {
            UserResponseDto userResponseDto = userService.signup(signUpRequestDto);
        } catch (SignUpUserExistsException ex) {
            assertEquals(ex.getMessage(), "이미 존재하는 회원입니다.");
        }
    }
}
