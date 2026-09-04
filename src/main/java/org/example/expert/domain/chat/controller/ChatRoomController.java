package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.dto.ChatRoomResponse;
import org.example.expert.domain.chat.dto.CreateRoomRequest;
import org.example.expert.domain.chat.service.ChatRoomService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponse> create(
            @RequestBody CreateRoomRequest request
    )
    {
        return ResponseEntity.ok(chatRoomService.createRoom(request.name()));
    }



}
