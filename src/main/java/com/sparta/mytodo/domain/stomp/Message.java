package com.sparta.mytodo.domain.stomp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String userName;
    private String content;
}