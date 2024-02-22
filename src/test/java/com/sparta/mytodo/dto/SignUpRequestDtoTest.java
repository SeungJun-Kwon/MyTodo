package com.sparta.mytodo.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignUpRequestDtoTest extends DtoTest {

    private static final String MSG_USERNAME_EMPTY = "사용자 이름이 공백이면 안됩니다.";
    private static final String MSG_USERNAME_LENGTH = "사용자 이름의 크기가 4에서 10 사이여야 합니다.";
    private static final String MSG_USERNAME_PATTERN = "사용자 이름은 영어(소문자)랑 숫자만 가능합니다.";
    private static final String MSG_PASSWORD_EMPTY = "사용자 비밀번호가 공백이면 안됩니다.";
    private static final String MSG_PASSWORD_LENGTH = "사용자 비밀번호의 크기가 8에서 15 사이여야 합니다.";
    private static final String MSG_PASSWORD_PATTERN = "사용자 비밀번호는 영어랑 숫자만 가능합니다.";

    @Test
    @DisplayName("유저 이름 공백 테스트")
    void 유저이름공백() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("").password("abcde123")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_USERNAME_EMPTY));
        assertTrue(messages.contains(MSG_USERNAME_LENGTH));
    }

    @Test
    @DisplayName("유저 이름 길이 테스트")
    void 유저이름길이1() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("abc")
            .password("abcde123")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_USERNAME_LENGTH));
    }

    @Test
    @DisplayName("유저 이름 길이 테스트")
    void 유저이름길이2() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("abcde12345123")
            .password("abcde123")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_USERNAME_LENGTH));
    }

    @Test
    @DisplayName("유저 이름 패턴 테스트")
    void 유저이름패턴() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("유저이름123")
            .password("abcde123")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_USERNAME_PATTERN));
    }

    @Test
    @DisplayName("유저 비밀번호 공백 테스트")
    void 유저비밀번호공백() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("abc123").password("")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_PASSWORD_EMPTY));
        assertTrue(messages.contains(MSG_PASSWORD_LENGTH));
    }

    @Test
    @DisplayName("유저 비밀번호 길이 테스트")
    void 유저비밀번호길이1() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("abc123")
            .password("abc123")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_PASSWORD_LENGTH));
    }

    @Test
    @DisplayName("유저 이름 길이 테스트")
    void 유저비밀번호길이2() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("abc123")
            .password("abcde12345123123123")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_PASSWORD_LENGTH));
    }

    @Test
    @DisplayName("유저 비밀번호 패턴 테스트")
    void 유저비밀번호패턴() {
        SignUpRequestDto requestDto = SignUpRequestDto.builder().username("abc123")
            .password("Abcd123!@#")
            .build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_PASSWORD_PATTERN));
    }
}