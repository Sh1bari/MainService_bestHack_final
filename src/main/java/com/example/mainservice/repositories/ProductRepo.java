package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Product;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);
    Page<Product> findAllByNameContainingIgnoreCase(String pattern, Pageable pageable);
}
