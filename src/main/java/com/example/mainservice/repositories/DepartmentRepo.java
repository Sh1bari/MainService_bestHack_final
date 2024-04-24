package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Department;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepo extends JpaRepository<Department, UUID> {
    Boolean existsByName(String name);

    Page<Department> findAll(Specification<Department> spec, Pageable pageable);
}
