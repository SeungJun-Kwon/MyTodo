package com.sparta.mytodo.domain.stomp;

import com.sparta.mytodo.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public List<GetChatRoomResponse> getChatRooms() {
        return chatRoomRepository.findAll().stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }

    public GetChatRoomResponse createChatRoom(CreateChatRoomRequest request, User user) {
        ChatRoom chatRoom = chatRoomRepository.save(
            ChatRoom.builder()
                .chatRoomName(request.getChatRoomName())
                .description(request.getDescription())
                .coverImage(request.getCoverImage())
                .user(user)
                .build()
        );

        return new GetChatRoomResponse(user.getUserName(), chatRoom);
    }

    public Long enterChatRoom(Long chatRoomId, User user) {
        Member member = memberRepository.save(
            Member.builder().chatRoomId(chatRoomId).userId(user.getUserId()).build());

        return member.getMemberId();
    }
}
