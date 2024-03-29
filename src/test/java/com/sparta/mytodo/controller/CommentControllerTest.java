package com.sparta.mytodo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodo.global.config.WebSecurityConfig;
import com.sparta.mytodo.domain.comment.controller.CommentController;
import com.sparta.mytodo.domain.comment.dto.CommentRequestDto;
import com.sparta.mytodo.domain.comment.dto.CommentResponseDto;
import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.comment.entity.Comment;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.security.UserRoleEnum;
import com.sparta.mytodo.global.security.UserDetailsImpl;
import com.sparta.mytodo.domain.comment.service.CommentService;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {CommentController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
class CommentControllerTest {

    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

    User user;
    Todo todo;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        mockSetup();
    }

    private void mockSetup() {
        // Mock 테스트 유저 생성
        Long userId = 100L;
        String email = "abc123@naver.com";
        String userName = "abc123";
        String password = "abc12345";
        UserRoleEnum role = UserRoleEnum.USER;
        user = new User(email, userName, password, role);
        user.setUserId(userId);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());

        TodoRequestDto todoRequestDto = TodoRequestDto.builder().todoName("카드 이름입니다")
            .content("카드 내용입니다!!!!!").build();
        todo = new Todo(todoRequestDto, user);
        todo.setTodoId(100L);
    }

    @Test
    @DisplayName("Create Comment")
    void createComment() throws Exception {
        // given
        CommentRequestDto requestDto = CommentRequestDto.builder().content("댓글 내용").build();
        Comment comment = new Comment(requestDto, user, todo);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        given(commentService.createComment(anyLong(), any(CommentRequestDto.class),
            any(User.class))).willReturn(commentResponseDto);

        // when-then
        mockMvc.perform(
                post("/api/comments/todo-id/100").content(postInfo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }
}