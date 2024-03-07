package com.sparta.mytodo.domain.user.service;

import com.sparta.mytodo.domain.user.dto.SignUpRequestDto;
import com.sparta.mytodo.domain.user.dto.UserResponseDto;
import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.security.UserRoleEnum;
import com.sparta.mytodo.domain.user.exception.SignUpUserExistsException;
import com.sparta.mytodo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Override
    @Transactional
    public UserResponseDto signup(SignUpRequestDto signUpRequestDto) {
        String email = signUpRequestDto.getEmail();
        String userName = signUpRequestDto.getUserName();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new SignUpUserExistsException("이미 존재하는 회원입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signUpRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(signUpRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(new User(email, userName, password, role));
        return new UserResponseDto(email, userName);
    }
}
