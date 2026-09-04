package org.example.expert.domain.chat.dto;

import org.example.expert.domain.user.entity.User;

public record ChatUserResponse(
        String userNickName
)
{
    public static ChatUserResponse from(User user) {
        return new ChatUserResponse(user.getNickname())
    }
}
