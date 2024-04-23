package com.example.mainservice.services;

import com.example.mainservice.exceptions.DepartmentNotFoundExc;
import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.models.requests.CreateDepartmentDtoReq;
import com.example.mainservice.repositories.DepartmentRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    public Department create(CreateDepartmentDtoReq req){
        Department department = new Department();
        department.setName(req.getName());
        departmentRepo.save(department);
        return department;
    }

    public Department findById(UUID id){
        return departmentRepo.findById(id)
                .orElseThrow(DepartmentNotFoundExc::new);
    }
}
