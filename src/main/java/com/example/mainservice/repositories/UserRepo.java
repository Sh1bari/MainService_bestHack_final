package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.User;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByAuthId(UUID id);
    List<User> findAllByAuthId(UUID id);
}
