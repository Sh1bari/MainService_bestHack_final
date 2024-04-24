package com.example.mainservice.services;

import com.example.mainservice.exceptions.PushHistoryNotFoundExc;
import com.example.mainservice.exceptions.PushNotFoundExc;
import com.example.mainservice.models.entities.Push;
import com.example.mainservice.models.entities.PushHistory;
import com.example.mainservice.repositories.PushHistoryRepo;
import com.example.mainservice.repositories.PushRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PushService {
    private final PushRepo pushRepo;
    private final PushHistoryRepo pushHistoryRepo;

    /*public Push createPush(){

    }*/
    public PushHistory findById(Long id){
        return pushHistoryRepo.findById(id)
                .orElseThrow(PushHistoryNotFoundExc::new);
    }

    public Push findById(UUID id){
        return pushRepo.findById(id)
                .orElseThrow(PushNotFoundExc::new);
    }
}
