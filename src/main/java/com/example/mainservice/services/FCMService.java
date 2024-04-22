package com.example.mainservice.services;

import com.google.firebase.messaging.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FCMService {

    public void sendNotification(String registrationToken, String title, String message){
        Message fcmMessage = Message.builder()
                .setToken(registrationToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build())
                .build();
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(fcmMessage);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully sent message: " + response);
    }

    public void sendNotification(List<String> tokens, String title, String body) {
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            System.out.println("Successfully sent multicast message: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Error sending multicast message: " + e.getMessage());
        }
    }
}
