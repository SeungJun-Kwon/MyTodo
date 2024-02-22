package com.sparta.mytodo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodo.config.WebSecurityConfig;
import com.sparta.mytodo.dto.TodoCardRequestDto;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import com.sparta.mytodo.security.UserDetailsImpl;
import com.sparta.mytodo.service.TodoCardService;
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
    controllers = {TodoCardController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class TodoCardControllerTest {

    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TodoCardService todoCardService;

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
        String username = "abc123";
        String password = "abc12345";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username, password, role);
        user.setId(userId);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    @Test
    @DisplayName("Create Card")
    void createCard() throws Exception {
        // given
        String cardname = "카드 이름";
        String content = "카드 내용 카드 내용 카드 내용";
        TodoCardRequestDto requestDto = TodoCardRequestDto.builder().cardname(cardname)
            .content(content).build();

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when-then
        mockMvc.perform(post("/api/cards").content(postInfo).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Get Cards")
    void getCards() throws Exception {
        // given

        // when-then
        mockMvc.perform(get("/api/cards").accept(MediaType.APPLICATION_JSON).principal(principal))
            .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("Get Cards By User")
    void getCardsByUser() throws Exception {
        // given

        // when-then
        mockMvc.perform(
                get("/api/cards/user-id/100").accept(MediaType.APPLICATION_JSON).principal(principal))
            .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("Update Card")
    void updateCard() throws Exception {
        // given
        String cardname = "카드 이름";
        String content = "카드 내용 카드 내용 카드 내용";
        TodoCardRequestDto requestDto = TodoCardRequestDto.builder().cardname(cardname)
            .content(content).build();

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when-then
        mockMvc.perform(
                put("/api/cards/card-id/100").content(postInfo).contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Finish Card")
    void finishCard() throws Exception {
        // given

        // when-then
        mockMvc.perform(put("/api/cards/card-id/100/is-finished/true")
                .accept(MediaType.APPLICATION_JSON).principal(principal)).andExpect(status().isOk())
            .andDo(print());
    }
}
