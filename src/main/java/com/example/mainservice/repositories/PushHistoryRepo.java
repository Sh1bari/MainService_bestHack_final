package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.PushHistory;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushHistoryRepo extends JpaRepository<PushHistory, Long> {
}
