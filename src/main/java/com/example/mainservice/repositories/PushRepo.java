package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Push;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PushRepo extends JpaRepository<Push, UUID> {
    Page<Push> findAll(Specification<Push> spec, Pageable pageable);
}
