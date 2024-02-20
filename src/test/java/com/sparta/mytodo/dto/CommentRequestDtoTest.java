package com.sparta.mytodo.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentRequestDtoTest extends DtoTest {

    private static final String MSG_COMMENT_EMPTY = "댓글이 공백이면 안됩니다.";
    private static final String MSG_COMMENT_LENGTH = "댓글의 길이는 2자 이상 1024자 이하여야 합니다.";

    @Test
    @DisplayName("댓글 공백 테스트")
    void 댓글공백() {
        CommentRequestDto requestDto = CommentRequestDto.builder().content("").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_COMMENT_EMPTY));
        assertTrue(messages.contains(MSG_COMMENT_LENGTH));
    }

    @Test
    @DisplayName("댓글 길이 테스트")
    void 댓글길이1() {
        CommentRequestDto requestDto = CommentRequestDto.builder().content("1").build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_COMMENT_LENGTH));
    }

    @Test
    @DisplayName("댓글 길이 테스트")
    void 댓글길이2() {
        CommentRequestDto requestDto = CommentRequestDto.builder().content("s".repeat(1025)).build();

        validateAndCollectMessages(requestDto);

        assertTrue(messages.contains(MSG_COMMENT_LENGTH));
    }
}