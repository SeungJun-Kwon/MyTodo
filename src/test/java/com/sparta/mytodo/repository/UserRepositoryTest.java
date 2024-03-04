package com.sparta.mytodo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sparta.mytodo.entity.User;
import com.sparta.mytodo.entity.UserRoleEnum;
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
        String username = "abc123";
        String password = "abc123";
        UserRoleEnum role = UserRoleEnum.USER;

        User createdUser = userRepository.save(new User(username, password, role));

        assertNotNull(createdUser.getId());
        assertEquals(username, createdUser.getUsername());
        assertEquals(password, createdUser.getPassword());
        assertEquals(role, createdUser.getRole());

        user = createdUser;
    }

    @Test
    @DisplayName("유저 수정")
    void updateUser() {
        user = userRepository.save(new User("abc123", "abc123", UserRoleEnum.USER));

        String username = "abc1234";
        if (userRepository.findById(user.getId()).isPresent()) {
            user.setUsername(username);
        }

        assertEquals(username, user.getUsername());
    }

    @Test
    @DisplayName("유저 삭제")
    void deleteUser() {
        user = userRepository.save(new User("abc123", "abc123", UserRoleEnum.USER));

        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.delete(user);
        }
        user = userRepository.findById(user.getId()).orElse(null);

        assertNull(user);
    }

    @Test
    void findByUsername() {
        user = userRepository.save(new User("abc123", "abc123", UserRoleEnum.USER));
        String username = "abc123";

        User findUser = userRepository.findByUsername(username).orElse(null);

        assertNotNull(findUser);
        assertEquals(user.getUsername(), findUser.getUsername());
    }
}
