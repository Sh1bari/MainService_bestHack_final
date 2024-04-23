package com.example.mainservice.services;

import com.example.mainservice.exceptions.UserNotFoundExc;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.repositories.UserRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User findByAuthId(UUID id){
        User user = userRepo.findByAuthId(id)
                .orElseThrow(UserNotFoundExc::new);
        return user;
    }

    public User createNewUser(CreateUserDto req){
        User user = User.builder()
                .id(req.getUserId())
                .authId(req.getUserId())
                .username(req.getUsername())
                .name(req.getName())
                .middleName(req.getMiddleName())
                .surname(req.getSurname())
                .createDate(LocalDateTime.now())
                .build();
        return userRepo.save(user);
    }
    public User linkTokenToUser(User user, String token){
        user.setPushToken(token);
        return userRepo.save(user);
    }


}
