package com.example.mainservice.services;

import com.example.mainservice.models.models.requests.MessageDto;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MessageService {

    public MessageDto sendMessage(MessageDto message){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/predict"; // Замените на URL вашего Flask API
        String requestJson = "{\"message\": \"" + message + "\"}";
        String predictedAnswer = restTemplate.postForObject(url, requestJson, String.class);
        return MessageDto.builder().message(predictedAnswer).build();
    }
}
