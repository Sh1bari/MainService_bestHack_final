package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByAuthId(UUID authid);
    Page<User> findAllByDepartmentRoles_department_id(UUID id, Pageable pageable);
}
