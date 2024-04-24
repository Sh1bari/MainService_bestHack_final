package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.entities.UserDepartmentRole;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDepartmentRoleRepo extends JpaRepository<UserDepartmentRole, Long> {
    Optional<UserDepartmentRole> findByUserAndDepartment(User user, Department department);
}
