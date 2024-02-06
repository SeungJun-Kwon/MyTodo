package com.sparta.mytodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCardRequestDto {
    @NotBlank(message = "제목이 공백이면 안됩니다.")
    @Size(min = 1, max = 50, message = "제목의 길이는 1자 이상 50자 이하여야 합니다.")
    private String cardname;

    @NotBlank(message = "내용이 공백이면 안됩니다.")
    @Size(min = 1, max = 1024, message = "내용의 길이는 1자 이상 1024자 이하여야 합니다.")
    private String content;

    private boolean isfinished;
}
