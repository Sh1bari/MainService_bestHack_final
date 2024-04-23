package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.UserDepartmentRole;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepartmentRoleRepo extends JpaRepository<UserDepartmentRole, Long> {
}
