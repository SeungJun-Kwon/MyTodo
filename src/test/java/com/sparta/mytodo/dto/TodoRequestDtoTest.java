package com.sparta.mytodo.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TodoRequestDtoTest extends DtoTest {


    private static final String MSG_CARDNAME_EMPTY = "제목이 공백이면 안됩니다.";
    private static final String MSG_CARDNAME_LENGTH = "제목의 길이는 5자 이상 50자 이하여야 합니다.";
    private static final String MSG_CONTENT_EMPTY = "내용이 공백이면 안됩니다.";
    private static final String MSG_CONTENT_LENGTH = "내용의 길이는 10자 이상 1024자 이하여야 합니다.";

    @Test
    @DisplayName("제목 공백 테스트")
    void 제목공백() {
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName("")
            .content("abcde12345").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_CARDNAME_EMPTY));
        assertTrue(messages.contains(MSG_CARDNAME_LENGTH));
    }

    @Test
    @DisplayName("제목 길이 테스트")
    void 제목길이1() {
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName("abc")
            .content("abcde12345").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_CARDNAME_LENGTH));
    }

    @Test
    @DisplayName("제목 길이 테스트")
    void 제목길이2() {
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName("s".repeat(51))
            .content("abcde12345").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_CARDNAME_LENGTH));
    }

    @Test
    @DisplayName("내용 공백 테스트")
    void 내용공백() {
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName("abc123")
            .content("").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_CONTENT_EMPTY));
        assertTrue(messages.contains(MSG_CONTENT_LENGTH));
    }

    @Test
    @DisplayName("내용 길이 테스트")
    void 내용길이1() {
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName("abc123")
            .content("abc123").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_CONTENT_LENGTH));
    }

    @Test
    @DisplayName("내용 길이 테스트")
    void 내용길이2() {
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName("abc123")
            .content("s".repeat(1025)).build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_CONTENT_LENGTH));
    }
}