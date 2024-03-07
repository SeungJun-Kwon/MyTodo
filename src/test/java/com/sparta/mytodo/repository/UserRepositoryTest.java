package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.domain.user.repository.UserRepository;
import com.sparta.mytodo.global.security.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

    @Test
    @DisplayName("유저 생성")
    void createUser() {
        String email = "abc123@naver.com";
        String userName = "abc123";
        String password = "abc123";
        UserRoleEnum role = UserRoleEnum.USER;

        User createdUser = userRepository.save(new User(email, userName, password, role));

        assertNotNull(createdUser.getUserId());
        assertEquals(userName, createdUser.getUserName());
        assertEquals(password, createdUser.getPassword());
        assertEquals(role, createdUser.getRole());

        user = createdUser;
    }

    @Test
    @DisplayName("유저 수정")
    void updateUser() {
        user = userRepository.save(new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER));

        String userName = "abc1234";
        if (userRepository.findById(user.getUserId()).isPresent()) {
            user.setUserName(userName);
        }

        assertEquals(userName, user.getUserName());
    }

    @Test
    @DisplayName("유저 삭제")
    void deleteUser() {
        user = userRepository.save(new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER));

        if (userRepository.findById(user.getUserId()).isPresent()) {
            userRepository.delete(user);
        }
        user = userRepository.findById(user.getUserId()).orElse(null);

        assertNull(user);
    }

    @Test
    void findByEmail() {
        user = userRepository.save(new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER));
        String email = "abc123@naver.com";

        User findUser = userRepository.findByEmail(email).orElse(null);

        assertNotNull(findUser);
        assertEquals(user.getEmail(), findUser.getEmail());
    }

    @Test
    void findByUserName() {
        user = userRepository.save(new User("abc123@naver.com", "abc123", "abc123", UserRoleEnum.USER));
        String userName = "abc123";

        User findUser = userRepository.findByUserName(userName);

        assertNotNull(findUser);
        assertEquals(userName, findUser.getUserName());
    }
}
