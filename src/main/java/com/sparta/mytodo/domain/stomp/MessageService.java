package com.sparta.mytodo.domain.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message createMessage(Long roomId, Message message) {
        if (roomId < 1) {
            throw new IllegalArgumentException("방 번호 오류 : " + roomId);
        }

        if (message.getUserName().isEmpty() || message.getContent().isEmpty()) {
            throw new IllegalArgumentException("메시지 입력값 Empty");
        }

        return messageRepository.save(
            new Message(message.getUserName(), message.getContent(), roomId));
    }
}
