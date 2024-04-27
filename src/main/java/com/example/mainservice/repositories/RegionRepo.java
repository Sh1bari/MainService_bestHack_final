package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Region;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepo extends JpaRepository<Region, Integer> {
    boolean existsByName(String name);
    Optional<Region> findByName(String name);
    List<Region> findAllByNameContainsIgnoreCase(String name);
}
