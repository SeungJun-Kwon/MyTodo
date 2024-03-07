package com.sparta.mytodo.global.security;

import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.domain.user.exception.SignUpUserExistsException;
import com.sparta.mytodo.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new SignUpUserExistsException("존재하지 않는 사용자 이메일입니다.")
        );

        return new UserDetailsImpl(user);
    }

    public UserDetails loadUserByClaims(Claims info) {
        User user = new User(info.getSubject(), info.get("userName").toString(), "",
            UserRoleEnum.valueOf(info.get(JwtUtil.AUTHORIZATION_KEY).toString()));
        user.setUserId((long) (int) info.get("userId"));

        return new UserDetailsImpl(user);
    }
}