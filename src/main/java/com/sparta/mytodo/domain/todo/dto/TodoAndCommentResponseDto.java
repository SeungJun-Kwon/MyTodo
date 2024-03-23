package com.sparta.mytodo.domain.todo.dto;

import com.sparta.mytodo.domain.comment.dto.CommentResponseDto;
import com.sparta.mytodo.domain.comment.entity.Comment;
import com.sparta.mytodo.domain.todo.entity.Todo;
import com.sparta.mytodo.domain.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class TodoAndCommentResponseDto {

    TodoResponseDto responseDto;
    List<CommentResponseDto> commentResponseDtoList;

    public TodoAndCommentResponseDto(Todo todo, User user, List<Comment> commentList) {
        responseDto = new TodoResponseDto(todo, user);
        commentResponseDtoList = commentList.stream().map(CommentResponseDto::new).toList();
    }
}
