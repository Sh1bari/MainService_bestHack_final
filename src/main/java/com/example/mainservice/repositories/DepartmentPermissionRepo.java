package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.DepartmentPermission;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentPermissionRepo extends JpaRepository<DepartmentPermission, UUID> {
}
