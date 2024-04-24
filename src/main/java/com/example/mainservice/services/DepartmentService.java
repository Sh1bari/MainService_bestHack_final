package com.example.mainservice.services;

import com.example.mainservice.exceptions.DepartmentNotFoundExc;
import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.ConnectUserToDepartmentDtoReq;
import com.example.mainservice.models.models.requests.CreateDepartmentDtoReq;
import com.example.mainservice.repositories.DepartmentRepo;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepo departmentRepo;
    private final UserDepartmentRoleService userDepartmentRoleService;
    private final UserService userService;

    public Department create(CreateDepartmentDtoReq req){
        Department department = new Department();
        department.setName(req.getName());
        departmentRepo.save(department);
        return department;
    }

    public Department update(CreateDepartmentDtoReq req, UUID id){
        Department department = findById(id);
        department.setName(req.getName());
        departmentRepo.save(department);
        return department;
    }

    public Department findById(UUID id){
        return departmentRepo.findById(id)
                .orElseThrow(DepartmentNotFoundExc::new);
    }

    public Department connectUserToDepartment(UUID departmentId,
                                              UUID userId,
                                              ConnectUserToDepartmentDtoReq req){
        Department department = findById(departmentId);
        User user = userService.findById(userId);
        userDepartmentRoleService.connectUserToDepartment(user, department, req.getRoles());
        return department;
    }

    public Page<Department> findByPage(Pageable pageable){
        return departmentRepo.findAll(pageable);
    }
}
