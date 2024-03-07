package com.sparta.mytodo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodo.global.config.WebSecurityConfig;
import com.sparta.mytodo.domain.todo.controller.TodoController;
import com.sparta.mytodo.domain.todo.dto.TodoRequestDto;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.security.UserRoleEnum;
import com.sparta.mytodo.global.security.UserDetailsImpl;
import com.sparta.mytodo.domain.todo.service.TodoService;
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
    controllers = {TodoController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class TodoControllerTest {

    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        mockUserSetup();
    }

    private void mockUserSetup() {
        // Mock 테스트 유저 생성
        Long userId = 100L;
        String email = "abc123@naver.com";
        String userName = "abc123";
        String password = "abc12345";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(email, userName, password, role);
        user.setUserId(userId);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    @Test
    @DisplayName("Create Todo")
    void createTodo() throws Exception {
        // given
        String todoName = "Todo 이름";
        String content = "Todo 내용 Todo 내용 Todo 내용";
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName(todoName)
            .content(content).build();

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when-then
        mockMvc.perform(post("/api/todos").content(postInfo).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Get Todos")
    void getTodos() throws Exception {
        // given

        // when-then
        mockMvc.perform(get("/api/todos").accept(MediaType.APPLICATION_JSON).principal(principal))
            .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("Get Todos By User")
    void getTodosByUser() throws Exception {
        // given

        // when-then
        mockMvc.perform(
                get("/api/todos/user-id/100").accept(MediaType.APPLICATION_JSON).principal(principal))
            .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("Update Todo")
    void updateTodo() throws Exception {
        // given
        String todoName = "Todo 이름";
        String content = "Todo 내용 Todo 내용 Todo 내용";
        TodoRequestDto requestDto = TodoRequestDto.builder().todoName(todoName)
            .content(content).build();

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when-then
        mockMvc.perform(
                put("/api/todos/100").content(postInfo).contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Finish Todo")
    void finishTodo() throws Exception {
        // given

        // when-then
        mockMvc.perform(patch("/api/cards/100?finished=true")
                .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }
}
