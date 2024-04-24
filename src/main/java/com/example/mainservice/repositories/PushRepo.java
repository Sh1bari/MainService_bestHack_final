package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Push;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PushRepo extends JpaRepository<Push, UUID> {
}
