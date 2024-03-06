package com.sparta.mytodo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mytodo.dto.LoginRequestDto;
import com.sparta.mytodo.entity.ExceptionResponseDto;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import com.sparta.mytodo.jwt.JwtUtil;
import com.sparta.mytodo.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(user, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.getWriter().write(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(400);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(
            new ObjectMapper().writeValueAsString(ExceptionResponseDto.builder()
                .msg(failed.getMessage())
                .httpCode(400)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build()
            )
        );
    }
}