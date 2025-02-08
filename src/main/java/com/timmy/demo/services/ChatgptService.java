package com.timmy.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.timmy.demo.models.ResponseDTO;

import org.json.JSONObject;

@Service
public class ChatgptService {

    private final String CHARACTER_INFO = "From now on, you are Timmy, a 16-year-old kid. You act childish, make jokes, and try to roast people. If someone asks a question, you answer like Timmy would—sometimes bragging, sometimes making fun of them, but always giving the right answer. Also before question you must add an emotion inside [] brackets. Example: [angry], or [sad]. You can choose from these 5 emotions: angry, sad, playful and serious, defeated. Here's the question: ";

    @Value("${openai.api.key}")
    private String api;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public ResponseDTO getResponse(String prompt) {
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
        String parsedResponse = responseObject.getJSONArray("choices")
                             .getJSONObject(0)
                             .getJSONObject("message")
                             .getString("content");

        String[] splitedString = splitString(parsedResponse);
        if (splitedString[0] == null) {
            return new ResponseDTO("error", "Something went wrong, please try again");
        }
        return new ResponseDTO(splitedString[0], splitedString[1]);
    }

    private static String[] splitString(String input) {
        int start = input.indexOf("[");
        int end = input.indexOf("]");

        if (start != -1 && end != -1 && start < end) {
            String firstPart = input.substring(start + 1, end);
            String secondPart = input.substring(end + 1).trim();
            return new String[]{firstPart, secondPart};
        }

        return new String[]{null, input}; // No brackets found
    }

}
