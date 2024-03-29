package com.sparta.mytodo.domain.stomp;

import com.sparta.mytodo.domain.user.entity.User;
import com.sparta.mytodo.global.security.JwtUtil;
import com.sparta.mytodo.global.security.UserDetailsImpl;
import com.sparta.mytodo.global.security.UserDetailsServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public GetMessageResponse createMessage(Long roomId, CreateMessageRequest request,
        String token) {
        if (roomId < 1) {
            throw new IllegalArgumentException("방 번호 오류 : " + roomId);
        }

        if (request.getContent().isEmpty()) {
            throw new IllegalArgumentException("메시지 입력값 Empty");
        }

        User user = ((UserDetailsImpl) userDetailsService.loadUserByClaims(
            jwtUtil.getUserInfoFromToken(token))).getUser();

        Message message = messageRepository.save(
            new Message(request.getContent(), user.getUserId(), roomId));

        return new GetMessageResponse(user.getUserName(), message.getMessageId(),
            message.getContent());
    }

    public List<GetMessageResponse> getRoomMessages(Long roomId) {
        List<GetMessageResponse> responseList = messageRepository.findAllByRoomId(roomId);

        return responseList;
    }
}
