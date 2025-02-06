package com.timmy.demo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timmy.demo.services.ChatgptService;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/timmy")
public class ChatgptController {

    private ChatgptService chatgptService;

    public ChatgptController(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> ask(@RequestBody String prompt) {
        String responseText = chatgptService.getResponse(prompt);
        return ResponseEntity.ok(Collections.singletonMap("response", responseText));  // Wrapping in a JSON object
    }

    @GetMapping("/test")
    public String test() {
        return new String("Testing: test");
    }
    

}
