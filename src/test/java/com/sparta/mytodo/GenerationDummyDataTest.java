//package com.sparta.mytodo;
//
//import com.sparta.mytodo.domain.comment.entity.Comment;
//import com.sparta.mytodo.domain.comment.repository.CommentRepository;
//import com.sparta.mytodo.domain.todo.entity.Todo;
//import com.sparta.mytodo.domain.todo.repository.TodoRepository;
//import com.sparta.mytodo.domain.user.entity.User;
//import com.sparta.mytodo.domain.user.repository.UserRepository;
//import com.sparta.mytodo.global.security.UserRoleEnum;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@SpringBootTest
//public class GenerationDummyDataTest {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private TodoRepository todoRepository;
//    @Autowired
//    private CommentRepository commentRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    public void generateTestData() {
//        // 10명의 랜덤한 유저 생성
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setEmail("user" + (i + 1) + "@example.com");
//            user.setUserName("User " + (i + 1));
//            user.setPassword(passwordEncoder.encode("password" + (i + 1)));
//            user.setRole(UserRoleEnum.USER);
//            users.add(user);
//        }
//        userRepository.saveAll(users);
//
//        // 50개의 랜덤한 Todo 생성
//        List<Todo> todos = new ArrayList<>();
//        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);
//        LocalDateTime endDate = LocalDateTime.now();
//        for (int i = 0; i < 50; i++) {
//            Todo todo = new Todo();
//            todo.setTodoName("Todo " + (i + 1));
//            todo.setContent("Content for Todo " + (i + 1));
//            todo.setFinished(Math.random() < 0.5); // 50% 확률로 finished 설정
//            todo.setUser(users.get((int) (Math.random() * users.size()))); // 랜덤한 유저 할당
//
//            // createdAt과 modifiedAt을 랜덤한 날짜로 설정
//            LocalDateTime randomDate = LocalDateTime.ofEpochSecond(
//                startDate.toEpochSecond(java.time.ZoneOffset.UTC) +
//                    (long) (Math.random() * (endDate.toEpochSecond(java.time.ZoneOffset.UTC) - startDate.toEpochSecond(java.time.ZoneOffset.UTC))),
//                0, java.time.ZoneOffset.UTC);
//            todo.setCreatedAt(randomDate);
//            todo.setModifiedAt(randomDate);
//
//            todos.add(todo);
//        }
//        todoRepository.saveAll(todos);
//
//        // 500개의 랜덤한 Comment 생성
//        List<Comment> comments = new ArrayList<>();
//        for (int i = 0; i < 500; i++) {
//            Comment comment = new Comment();
//            comment.setContent("Comment " + (i + 1));
//            comment.setUser(users.get((int) (Math.random() * users.size()))); // 랜덤한 유저 할당
//            comment.setTodo(todos.get((int) (Math.random() * todos.size()))); // 랜덤한 Todo 할당
//
//            // createdAt과 modifiedAt을 랜덤한 날짜로 설정
//            LocalDateTime randomDate = LocalDateTime.ofEpochSecond(
//                startDate.toEpochSecond(java.time.ZoneOffset.UTC) +
//                    (long) (Math.random() * (endDate.toEpochSecond(java.time.ZoneOffset.UTC) - startDate.toEpochSecond(java.time.ZoneOffset.UTC))),
//                0, java.time.ZoneOffset.UTC);
//            comment.setCreatedAt(randomDate);
//            comment.setModifiedAt(randomDate);
//
//            comments.add(comment);
//        }
//        commentRepository.saveAll(comments);
//    }
//}