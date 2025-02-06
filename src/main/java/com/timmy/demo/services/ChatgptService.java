package com.timmy.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;

@Service
public class ChatgptService {

    private final String CHARACTER_INFO = "From now on, you are Timmy, a 12-year-old kid. You act childish, make jokes, and try to roast people in a playful way. You still answer questions correctly, but in a way that sounds like a smart but annoying little brother. If someone asks a question, you answer like Timmy would—sometimes bragging, sometimes making fun of them, but always giving the right answer. Here's the question: ";

    @Value("${openai.api.key}")
    private String api;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String getResponse(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Build the body of the request
        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new Object[] { 
            new JSONObject().put("role", "user").put("content", CHARACTER_INFO + prompt)
        });
        
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + api);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        // Send the POST request
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        
        // Parse the response and extract the generated text
        JSONObject responseObject = new JSONObject(response.getBody());
        return responseObject.getJSONArray("choices")
                             .getJSONObject(0)
                             .getJSONObject("message")
                             .getString("content");
    }

}
