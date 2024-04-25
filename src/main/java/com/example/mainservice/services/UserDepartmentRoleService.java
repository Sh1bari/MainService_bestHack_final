package com.example.mainservice.services;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.entities.UserDepartmentRole;
import com.example.mainservice.models.enums.UserRoleInDepartment;
import com.example.mainservice.repositories.UserDepartmentRoleRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDepartmentRoleService {
    private final UserDepartmentRoleRepo userDepartmentRoleRepo;

    public UserDepartmentRole connectUserToDepartment(User user,
                                                      Department department,
                                                      List<UserRoleInDepartment> roles){
        UserDepartmentRole udr = userDepartmentRoleRepo.findByUserAndDepartment(user, department)
                        .orElse(new UserDepartmentRole());
        udr.setUser(user);
        udr.setDepartment(department);
        udr.setRoles(roles);
        if(roles.size() == 0){
            userDepartmentRoleRepo.delete(udr);
            return udr;
        }
        return userDepartmentRoleRepo.save(udr);
    }
}
