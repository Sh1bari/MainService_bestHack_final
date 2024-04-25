package com.example.mainservice.services;

import com.example.mainservice.exceptions.UserNotFoundExc;
import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.DepartmentPermission;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.enums.UserRole;
import com.example.mainservice.models.models.requests.CreateUserDto;
import com.example.mainservice.models.models.requests.SendToDepartmentRolesDto;
import com.example.mainservice.models.models.requests.SendToUserIdDto;
import com.example.mainservice.models.models.requests.UpdateUserDtoReq;
import com.example.mainservice.repositories.UserRepo;
import com.example.mainservice.specifications.UserSpecifications;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public Set<User> getUsersForSend(List<SendToDepartmentRolesDto> toDepartmentRoles,
                                     List<SendToUserIdDto> toUserId){
        Set<User> res = new HashSet<>();
        toDepartmentRoles.forEach(o->{
            res.addAll(userRepo.findAllByDepartmentRoles_department_idAndDepartmentRoles_rolesIn(o.getDepartmentId(), Collections.singleton(o.getRoles())));
        });
        res.addAll(userRepo.findAllByIds(toUserId.stream().map(SendToUserIdDto::getUserId).toList()));
        return res;
    }

    public User findByAuthId(UUID id){
        User user = userRepo.findByAuthId(id)
                .orElseThrow(UserNotFoundExc::new);
        return user;
    }
    public User findById(UUID id){
        User user = userRepo.findById(id)
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
                .globalRole(UserRole.ROLE_USER)
                .build();
        return userRepo.save(user);
    }
    public User linkTokenToUser(User user, String token){
        user.getPushTokens().add(token);
        return userRepo.save(user);
    }
    public Page<User> getUserPage(Specification<User> spec, Pageable pageable){
        return userRepo.findAll(spec, pageable);
    }
    public Page<User> getUserPageCanSend(User user, Department department, Specification<User> spec, Pageable pageable){
        return userRepo.findAll(spec, pageable);
    }

    public User updateUserInfo(UUID userId, UpdateUserDtoReq req){
        User user = findById(userId);
        user.setName(req.getName());
        user.setMiddleName(req.getMiddleName());
        user.setSurname(req.getSurname());
        return userRepo.save(user);
    }

    public Page<User> getUsersByDepartmentId(UUID departmentId, Pageable pageable){
        return userRepo.findAllByDepartmentRoles_department_id(departmentId, pageable);
    }


}
