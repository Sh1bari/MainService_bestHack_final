package com.example.mainservice.services;

import com.example.mainservice.repositories.DepartmentRepo;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    //public
}
