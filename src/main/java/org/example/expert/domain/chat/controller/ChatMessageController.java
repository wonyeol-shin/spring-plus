package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.service.ChatMessageService;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
}
