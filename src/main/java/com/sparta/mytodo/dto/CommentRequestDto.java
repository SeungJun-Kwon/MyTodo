package com.sparta.mytodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글이 공백이면 안됩니다.")
    @Size(min = 2, max = 1024, message = "댓글의 길이는 2자 이상 1024자 이하여야 합니다.")
    private String content;
}
