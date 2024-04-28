package com.example.mainservice.services;

import com.example.mainservice.exceptions.UserNotFoundExc;
import com.example.mainservice.models.entities.Order;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.OrderStatus;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.models.models.requests.UpdateUserDtoReq;
import com.example.mainservice.models.models.responses.AchievementDtoRes;
import com.example.mainservice.repositories.OrderRepo;
import com.example.mainservice.repositories.UserRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final RoleService roleService;

    public User findById(UUID id){
        return userRepo.findById(id)
                .orElseThrow(UserNotFoundExc::new);
    }
    public User findByAuthId(UUID id){
        return userRepo.findByAuthId(id)
                .orElseThrow(UserNotFoundExc::new);
    }

    public User createNewUserByAuthService(CreateUserDto u){
        User user = new User();
        user.setName(u.getName());
        user.setUsername(u.getUsername());
        user.setAuthId(u.getUserId());
        user.setSurname(u.getSurname());
        user.setMiddleName(u.getMiddleName());
        user.setPhoneNumber(u.getPhoneNumber());
        user.getRoles().add(roleService.getUserRole());
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.DRAFT);
        orderRepo.save(order);
        return userRepo.save(user);
    }

    public User updateMe(User user, UpdateUserDtoReq u){
        user.setName(u.getName());
        user.setSurname(u.getSurname());
        user.setMiddleName(u.getMiddleName());
        return userRepo.save(user);
    }

}
