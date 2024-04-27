package com.example.mainservice.repositories;

import com.example.mainservice.models.entities.Order;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
