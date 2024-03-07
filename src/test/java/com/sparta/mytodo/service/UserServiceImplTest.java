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
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입")
    void signUp() {
        // given
        String userName = "abc123";
        String password = "abc12345";

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().userName(userName)
            .password(password).build();

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder);

        // when
        UserResponseDto userResponseDto = userServiceImpl.signup(signUpRequestDto);

        // then
        assertNotNull(userResponseDto);
        assertEquals(userName, userResponseDto.getUserName());
    }

    @Test
    @DisplayName("회원 가입 실패(유저 이미 존재)")
    void signUpUserExists() {
        // given
        String userName = "abc123";
        String password = "abc12345";

        User user = new User();
        user.setUserId(100L);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(UserRoleEnum.USER);
        userRepository.save(user);

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().userName(userName)
            .password(password).build();

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder);

        // when - then
        try {
            UserResponseDto userResponseDto = userServiceImpl.signup(signUpRequestDto);
        } catch (SignUpUserExistsException ex) {
            assertEquals(ex.getMessage(), "이미 존재하는 회원입니다.");
        }
    }
}
