package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Department;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepo extends JpaRepository<Department, UUID> {
}
