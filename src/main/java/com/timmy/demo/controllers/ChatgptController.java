package com.timmy.demo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timmy.demo.models.ResponseDTO;
import com.timmy.demo.services.ChatgptService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/timmy")
public class ChatgptController {

    private ChatgptService chatgptService;

    public ChatgptController(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    @PostMapping("/ask")
    public ResponseDTO ask(@RequestBody String prompt) {
        return chatgptService.getResponse(prompt);
    }

    @GetMapping("/test")
    public String test() {
        return new String("Testing: test");
    }

}
