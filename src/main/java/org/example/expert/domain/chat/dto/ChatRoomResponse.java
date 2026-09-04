package org.example.expert.domain.chat.dto;

import org.example.expert.domain.chat.entity.ChatRoom;
import org.example.expert.domain.user.entity.User;

import java.util.List;

public record ChatRoomResponse(
        Long id,
        String name,
        List<ChatUserResponse> users
)
{
    public static ChatRoomResponse from(ChatRoom chatRoom, List<User> users) {
      return new ChatRoomResponse(
              chatRoom.getId(),
              chatRoom.getName(),
              users.stream().map((user ->
                      new ChatUserResponse(user.getNickname()))).toList());
    }
}
