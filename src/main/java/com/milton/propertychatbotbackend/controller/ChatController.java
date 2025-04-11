package com.milton.propertychatbotbackend.controller;

import com.milton.propertychatbotbackend.dto.ChatDto;
import com.milton.propertychatbotbackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public Mono<Map<String, String>> getChatResponse(@RequestBody ChatDto request) {
        String userMessage = request.getMessage();
        return chatService.getChatResponse(userMessage)
                .map(response -> Map.of("response", response));
    }
}