package com.sparta.mytodo.domain.user.service;

import com.sparta.mytodo.domain.user.dto.SignUpRequestDto;
import com.sparta.mytodo.domain.user.dto.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    /**
     * 회원 가입
     * @param signUpRequestDto : 회원가입 요청 정보(이메일, 이름, 비밀번호)
     * @return 유저 생성 결과
     */
    @Transactional
    UserResponseDto signup(SignUpRequestDto signUpRequestDto);
}
