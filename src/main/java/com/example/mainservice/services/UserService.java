package com.example.mainservice.services;

import com.example.mainservice.exceptions.UserNotFoundExc;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.repositories.UserRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User findById(UUID id){
        return userRepo.findById(id)
                .orElseThrow(UserNotFoundExc::new);
    }
    public User findByAuthId(UUID id){
        return userRepo.findByAuthId(id)
                .orElseThrow(UserNotFoundExc::new);
    }
}
