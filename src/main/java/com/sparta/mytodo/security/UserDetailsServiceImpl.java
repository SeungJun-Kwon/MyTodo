package com.sparta.mytodo.security;

import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
import com.sparta.mytodo.jwt.JwtUtil;
import com.sparta.mytodo.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Not Found " + username)
        );

        return new UserDetailsImpl(user);
    }

    public UserDetails loadUserByClaims(Claims info) {
        User user = new User(info.getSubject(), "",
            UserRoleEnum.valueOf(info.get(JwtUtil.AUTHORIZATION_KEY).toString()));

        return new UserDetailsImpl(user);
    }
}