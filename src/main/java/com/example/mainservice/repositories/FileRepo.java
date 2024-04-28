package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.File;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
