package com.sparta.mytodo.service;

import com.sparta.mytodo.dto.UserRequestDto;
import com.sparta.mytodo.dto.UserResponseDto;
import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.jwt.JwtUtil;
import com.sparta.mytodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        userRepository.save(new User(username, password));
        return new UserResponseDto(username);
    }

    public String login(UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다.")
        );

        if(!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(userRequestDto.getUsername());
    }

    public String getCookie(String bearerToken) {
        String tokenValue = jwtUtil.getJwtFromToken(bearerToken);

        return jwtUtil.getUserInfoFromToken(tokenValue).getSubject();
    }
}
