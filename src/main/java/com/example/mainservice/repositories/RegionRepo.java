package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Region;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepo extends JpaRepository<Region, Integer> {
    boolean existsByName(String name);
    List<Region> findAllByNameContainingIgnoreCase(String name);
}
